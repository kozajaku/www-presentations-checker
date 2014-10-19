package org.presentation.webcrawler.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.model.graph.Edge;
import org.presentation.model.graph.InvalidNode;
import org.presentation.model.graph.Node;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.graph.ValidNode;
import org.presentation.model.logging.ErrorCode;
import org.presentation.model.logging.ErrorMsg;
import org.presentation.model.logging.InfoMsg;
import org.presentation.model.logging.InvalidLinkMsg;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.graph.LinkSourceType;
import org.presentation.parser.CSSParserService;
import org.presentation.parser.HTMLParserService;
import org.presentation.parser.ParsedLinkResponse;
import org.presentation.webcrawler.CompleteCrawlingState;
import org.presentation.webcrawler.CrawlerService;
import org.presentation.webcrawler.CrawlingState;
import org.presentation.webcrawler.PageCrawlingObserver;

/**
 * Default implementation of CrawlerService interface.
 *
 * @author Adam Kugler
 * @version 1.0
 */
@Default
@Dependent
public class CrawlerServiceDefault implements CrawlerService {

    public class WebPage {

        private final String label;
        private final LinkSourceType linkSourceType;
        private final ValidNode previousNode;
        private final LinkURL linkURL;
        private final int depthFromRoot;

        /**
         * This class helps crawled to crawl the web and to create traversal
         * graph.
         *
         * @param label Name of link that points to page
         * @param linkSourceType Type of link source
         * @param previousNode Node from which was the page found.
         * @param linkURL URL of page
         * @param depthFromRoot How long is link connection (number of links)
         * between root page and this page
         */
        public WebPage(String label, LinkSourceType linkSourceType, ValidNode previousNode, LinkURL linkURL, int depthFromRoot) {
            this.label = label;
            this.linkSourceType = linkSourceType;
            this.previousNode = previousNode;
            this.linkURL = linkURL;
            this.depthFromRoot = depthFromRoot;
        }

        /**
         * This method checks if page is available, sends it to further process
         * and gets new links from it.
         *
         * @return List of newly found web pages (links).
         */
        public List<WebPage> browseWebPage() {
            List<WebPage> foundPages = new ArrayList<>();
            foundPages.clear();
            //has been node already created? (URL has been checked)
            Node createdNode = visitedURLs.get(linkURL);
            if (createdNode != null) {
                previousNode.addEdge(new Edge(createdNode, label, linkSourceType, false));
                return foundPages;
            }
            ReceiverResponse receiverResponse;
            //is URL unreachable?
            Boolean tryToReach = unreachedURLs.get(linkURL);
            if (tryToReach != null && tryToReach == false) {
                return foundPages;
            }
            //stop conditions
            //LOG.log(Level.INFO, "test condition - pageLimit: {0}, maxDepth: {1}, !allowedURL: {2}", new Object[]{Boolean.toString(isOverPageLimit()), Boolean.toString(isOverMaximalDepth()), Boolean.toString(!isAllowedURL(linkURL))});
            if (isOverPageLimit() || isOverMaximalDepth() || !isAllowedURL(linkURL)) {
                //check page
                LOG.info("just check page (HEAD)");
                try {
                    LOG.log(Level.INFO, "sleep for 100 ms");
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
                try {
                    receiverResponse = pageReceiver.checkPage(linkURL, headers);
                } catch (UnknownHostException ex) {
                    LOG.log(Level.WARNING, "Unable to resolve DNS name: {0}", ex.getMessage());
                    //don't try to resolve DNS more than twice; node is not in graph
                    if (tryToReach == null) {
                        //try it again later
                        unreachedURLs.put(linkURL, true);
                        linkQueue.add(this);
                    } else {
                        //don't try it anymore
                        unreachedURLs.put(linkURL, false);
                        sendErrorMsg(linkURL, "Unable to resolve DNS name.");
                    }
                    return foundPages;
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                    sendErrorMsg(linkURL, "Unable to get page.");
                    return foundPages;
                }
            } else {
                //receive page
                LOG.log(Level.INFO, "get page {0}(GET)", crawlingState.getPagesCrawled());
                try {
                    LOG.log(Level.INFO, "sleep for {0} ms", requestTimeout);
                    Thread.sleep(requestTimeout);
                } catch (InterruptedException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
                try {
                    receiverResponse = pageReceiver.getPage(linkURL, headers);
                } catch (UnknownHostException ex) {
                    LOG.log(Level.WARNING, "Unable to resolve DNS name: {0}", ex.getMessage());
                    //don't try to resolve DNS more than twice; node is not in graph
                    if (tryToReach == null) {
                        //try it again later
                        unreachedURLs.put(linkURL, true);
                        linkQueue.add(this);
                    } else {
                        //don't try it anymore
                        unreachedURLs.put(linkURL, false);
                        sendErrorMsg(linkURL, "Unable to resolve DNS name.");
                    }
                    return foundPages;
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                    sendErrorMsg(linkURL, "Unable to get page.");
                    return foundPages;
                }
                crawlingState.incPagesCrawled();
                //send page to further process
                if (receiverResponse.isOK() && receiverResponse.getContentType().isProcessable()) {
                    observer.processOnePage(linkURL, receiverResponse.getSourceCode(), receiverResponse.getContentType());
                }
            }
            //create node
            if (receiverResponse.isOK()) {
                LOG.info("create valid node");
                ValidNode node = new ValidNode(linkURL);
                //connect with graph
                if (previousNode != null) {
                    previousNode.addEdge(new Edge(node, label, linkSourceType, true));
                } else {
                    graph = new TraversalGraph(node);
                }
                visitedURLs.put(linkURL, node);
                sendValidLinkMsg(linkURL);
                //find links in page
                List<ParsedLinkResponse> foundLinks;
                LOG.info("get links from page");
                foundLinks = getLinksFromPage(receiverResponse, linkURL);
                //check if link was already found
                for (ParsedLinkResponse foundLink : foundLinks) {
                    LinkURL foundURL = foundLink.getLink();
                    if (visitedURLs.containsKey(foundURL)) {
                        //add edge
                        //LOG.info("add edge (existing link)");
                        Edge newEdge = new Edge(visitedURLs.get(foundURL), foundLink.getLabel(), foundLink.getSourceType(), false);
                        node.addEdge(newEdge);
                    } else {
                        //add to queue
                        //LOG.info("add to queue (new link)");
                        foundPages.add(new WebPage(foundLink.getLabel(), foundLink.getSourceType(), node, foundURL, depthFromRoot + 1));
                    }
                }
            } else {
                LOG.info("create invalid node");
                InvalidNode node = new InvalidNode(linkURL, new ErrorCode(receiverResponse.getStateCode()));
                //connect with graph
                if (previousNode != null) {
                    previousNode.addEdge(new Edge(node, label, linkSourceType, true));
                } else {
                    graph = new TraversalGraph(node);
                }
                visitedURLs.put(linkURL, node);
                sendInvalidLinkMsg(linkURL, new ErrorCode(receiverResponse.getStateCode()));
            }
            return foundPages;
        }

        /**
         * Decides if page is over allowed depth.
         *
         * @return True if page is over allowed depth.
         */
        public boolean isOverMaximalDepth() {
            //LOG.info("is over maximal depth?");
            if (depthFromRoot > maximalDepth) {
                if (completeCrawlingState == CompleteCrawlingState.UNKNOWN) {
                    completeCrawlingState = CompleteCrawlingState.ENDED_BY_DEPTH;
                }
                return true;
            }
            return false;
        }

        /**
         * Decides if page limit has been reached.
         *
         * @return True if page limit has been reached.
         */
        public boolean isOverPageLimit() {
            //LOG.info("is over page limit?");
            if (crawlingState.getPagesCrawled() >= pageLimit) {
                if (completeCrawlingState == CompleteCrawlingState.UNKNOWN) {
                    completeCrawlingState = CompleteCrawlingState.ENDED_BY_PAGE_LIMIT;
                }
                return true;
            }
            return false;
        }
    }

    /**
     * This object is instantiated by registering into MessageLoggerContainer.
     */
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    private MessageLogger messageLogger;
    private PageCrawlingObserver observer;
    private TraversalGraph graph;
    private List<Domain> allowedDomains;
    private int maximalDepth;
    private Queue<WebPage> linkQueue;
    @Inject
    private PageReceiver pageReceiver;
    private CrawlingState crawlingState;
    @Inject
    private CSSParserService cssParserService;
    @Inject
    private HTMLParserService htmlParserService;
    private int requestTimeout;
    private List<Header> headers;
    private int pageLimit;
    private boolean stopped = false;
    CompleteCrawlingState completeCrawlingState;

    private Map<LinkURL, Node> visitedURLs;
    /**
     * List of URLs that crawler can't reached because of exception. Boolean
     * value indicates if crawler schould keep trying to get this URL (true) or
     * not (false).
     */
    private Map<LinkURL, Boolean> unreachedURLs;

    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer container) {
        messageLogger = container.createLogger("Web crawler");
        pageReceiver.offerMsgLoggerContainer(container);
    }

    @Override
    public void startBrowsing(LinkURL url, int maximalDepth, int pageLimit, PageCrawlingObserver observer, List<Domain> allowedDomains, int requestTimeout, List<Header> addHeaders) {
        LOG.info("startBrowsing");
        this.maximalDepth = maximalDepth;
        this.pageLimit = pageLimit;
        this.observer = observer;
        this.allowedDomains = allowedDomains;
        this.requestTimeout = requestTimeout;
        this.headers = addHeaders;
        crawlingState = new CrawlingState();
        completeCrawlingState = CompleteCrawlingState.UNKNOWN;

        visitedURLs = new HashMap<>();
        unreachedURLs = new HashMap<>();

        linkQueue = new LinkedList<>();
        WebPage page = new WebPage(null, null, null, url, 0);
        linkQueue.add(page);
        while (!linkQueue.isEmpty() && !stopped) {
            //LOG.log(Level.INFO, "Processing new Page {0}", page.linkURL.getUrl());
            page = linkQueue.poll();
            linkQueue.addAll(page.browseWebPage());
        }
        LOG.info("crawling done");
        crawlingState.done();
        if (stopped) {
            completeCrawlingState = CompleteCrawlingState.STOPPED_BY_USER;
        } else {
            if (completeCrawlingState == CompleteCrawlingState.UNKNOWN) {
                completeCrawlingState = CompleteCrawlingState.WEB_CRAWLED;
            }
        }
        observer.crawlingDone(graph, completeCrawlingState);
    }

    /**
     * Gets all links from page using HTML and CSS parsers.
     *
     * @param response A result from page receiver
     * @param baseURL An absolute URL of page
     * @return
     */
    private List<ParsedLinkResponse> getLinksFromPage(ReceiverResponse response, LinkURL baseURL) {
        if (response.getContentType().isCss()) {
            LOG.info("CSS parse");
            return cssParserService.parseLinks(response.getSourceCode(), baseURL);
        }
        if (response.getContentType().isHtml()) {
            LOG.info("HTML parse");
            return htmlParserService.parseLinks(response.getSourceCode(), baseURL);
        }
        LOG.info("skip parsing");
        return new ArrayList<>();
    }

    /**
     * Sends message about valid link to message logger.
     *
     * @param url Location of valid link
     */
    private void sendValidLinkMsg(LinkURL url) {
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setMessage("valid link");
        infoMsg.setPage(url);
        messageLogger.addMessage(infoMsg);
    }

    /**
     * Sends message about invalid link to message logger.
     *
     * @param url Location of invalid link
     * @param errorCode Code of error (HTTP)
     */
    private void sendInvalidLinkMsg(LinkURL url, ErrorCode errorCode) {
        InvalidLinkMsg invalidLinkMsg = new InvalidLinkMsg();
        invalidLinkMsg.setMessage("invalid link");
        invalidLinkMsg.setPage(url);
        invalidLinkMsg.setErrorCode(errorCode);
        messageLogger.addMessage(invalidLinkMsg);
    }

    /**
     * Sends error message to message logger.
     *
     * @param url Location of error
     * @param error Message about error
     */
    private void sendErrorMsg(LinkURL url, String error) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setMessage(error);
        errorMsg.setPage(url);
        messageLogger.addMessage(errorMsg);
    }

    /**
     * Decides if URL is included in allowed domain list.
     *
     * @param url URL that is checked
     * @return True if url is allowed.
     */
    private boolean isAllowedURL(LinkURL url) {
        //LOG.log(Level.INFO, "is allowed URL? {0}", url.getUrl());
        String link = url.getUrl();
        String[] parts = link.split("/");
        String domainURL = parts[2];
        //cut port
        domainURL = domainURL.split(":")[0];
        for (Domain allowedDomain : allowedDomains) {
            String domain = allowedDomain.getDomain();
            if (domainURL.length() >= domain.length()) {
                //domeny se musi shodovat od konce
                if (domain.equals(domainURL.substring(domainURL.length() - domain.length()))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void stopChecking() {
        LOG.info("stop checking");
        stopped = true;
    }

    @Override
    public CrawlingState getCrawlingState() {
        return crawlingState;
    }

}
