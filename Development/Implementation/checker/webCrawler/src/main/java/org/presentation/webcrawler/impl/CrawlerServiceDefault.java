package org.presentation.webcrawler.impl;

import java.io.IOException;
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
import org.presentation.webcrawler.PageReceiver;
import org.presentation.webcrawler.ReceiverResponse;

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

        public WebPage(String label, LinkSourceType linkSourceType, ValidNode previousNode, LinkURL linkURL, int depthFromRoot) {
            this.label = label;
            this.linkSourceType = linkSourceType;
            this.previousNode = previousNode;
            this.linkURL = linkURL;
            this.depthFromRoot = depthFromRoot;
        }

//                public Node getPreviousNode() {
//                    return previousNode;
//                }
//
//                public LinkURL getLinkURL() {
//                    return linkURL;
//                }
        public List<WebPage> browseWebPage() {
            List<WebPage> foundPages = new ArrayList<>();
            foundPages.clear();
            ReceiverResponse receiverResponse;
            //podminky zastaveni
            if (isOverMaximalDepth() || !isAllowedURL(linkURL) || pageCounter > pageLimit) {
                try {
                    //nestahuj stranku
                    receiverResponse = pageReceiver.checkPage(linkURL, headers);
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                    //posli zpravu o chybe
                    sendErrorMsg(linkURL, "Unable to get page.");
                    return foundPages;
                }
            } else {
                //stahni stranku
                crawlingState.incCount();
                try {
                    receiverResponse = pageReceiver.getPage(linkURL, headers);
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                    //posli zpravu o chybe
                    sendErrorMsg(linkURL, "Unable to get page.");
                    return foundPages;
                }
            }
            //vytvor node
            if (200 <= receiverResponse.getStateCode() && receiverResponse.getStateCode() < 300) {
                ValidNode node = new ValidNode(linkURL);
                //spoj s grafem
                if (previousNode != null) {
                    previousNode.addEdge(new Edge(node, label, linkSourceType));
                } else {
                    graph = new TraversalGraph(node);
                }
                visitedURLs.put(linkURL, node);
                sendValidLinkMsg(linkURL);
                //najdi dalsi odkazy
                List<ParsedLinkResponse> foundLinks;
                foundLinks = getLinksFromPage(receiverResponse);
                //over jestli stranka existuje
                for (ParsedLinkResponse foundLink : foundLinks) {
                    LinkURL foundURL = foundLink.getDestination();
                    if (visitedURLs.containsKey(foundURL)) {
                        //pridej hranu
                        Edge newEdge = new Edge(visitedURLs.get(foundURL), foundLink.getLabel(), foundLink.getSourceType());
                        node.addEdge(newEdge);
                    } else {
                        //pridej do fronty
                        foundPages.add(new WebPage(foundLink.getLabel(), foundLink.getSourceType(), node, foundURL, depthFromRoot + 1));
                    }
                }
            } else {
                InvalidNode node = new InvalidNode(linkURL, new ErrorCode(receiverResponse.getStateCode()));
                //spoj s grafem
                if (previousNode != null) {
                    previousNode.addEdge(new Edge(node, label, linkSourceType));
                } else {
                    graph = new TraversalGraph(node);
                }
                visitedURLs.put(linkURL, node);
                sendInvalidLinkMsg(linkURL, new ErrorCode(receiverResponse.getStateCode()));
            }
            return foundPages;
        }

        public boolean isOverMaximalDepth() {
            return depthFromRoot <= maximalDepth;
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
    private PageReceiver pageReceiver;
    private CrawlingState crawlingState;
    @Inject
    private CSSParserService cssParserService;
    @Inject
    private HTMLParserService htmlParserService;
    /**
     * Initial 3000 ms timeout between requests.
     */
    private int requestTimeout;
    private List<Header> headers;
    private int pageLimit;
    private int pageCounter;
    private boolean stopped = false;

    private Map<LinkURL, Node> visitedURLs;

    /**
     *
     * @param container
     */
    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer container) {
        messageLogger = container.createLogger("Web crawler");
    }

    /**
     *
     * @param url
     * @param maximalDepth
     * @param observer Observer object, into which the CrawlerService
     * implementation will send its results.
     * @param allowedDomains
     */
    @Override
    public void startBrowsing(LinkURL url, int maximalDepth, int pageLimit, PageCrawlingObserver observer, List<Domain> allowedDomains, int requestTimeout, List<Header> addHeaders) {
        //TODO add support for pageLimit and headers - by radio.koza
        this.maximalDepth = maximalDepth;
        this.pageLimit = pageLimit;
        this.observer = observer;
        this.allowedDomains = allowedDomains;
        this.requestTimeout = requestTimeout;
        this.headers = addHeaders;
        crawlingState = new CrawlingState();
        pageReceiver = new PageReceiver(messageLogger);
        
        visitedURLs = new HashMap<>();
        linkQueue = new LinkedList<>();
        WebPage page = new WebPage(null, null, null, url, 0);
        linkQueue.add(page);
        while (!linkQueue.isEmpty() && !stopped) {
            page = linkQueue.poll();
            linkQueue.addAll(page.browseWebPage());
            try {
                Thread.sleep(requestTimeout);
            } catch (InterruptedException ex) {
                Logger.getLogger(CrawlerServiceDefault.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        crawlingState.done();
        observer.crawlingDone(graph, CompleteCrawlingState.ENDED_BY_DEPTH);
    }

    //dostan vsechny odkazy ze stranky
    private List<ParsedLinkResponse> getLinksFromPage(ReceiverResponse response) {
        if (response.getContentType().getContentType().equals("text/css")) {
            return cssParserService.parseLinks(response.getSourceCode());
        }
        if (response.getContentType().getContentType().equals("text/html")) {
            return htmlParserService.parseLinks(response.getSourceCode());
        }
        return new ArrayList<>();
    }

    private void sendValidLinkMsg(LinkURL url) {
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setMessage("valid link");
        infoMsg.setPage(url);
        messageLogger.addMessage(infoMsg);
    }

    private void sendInvalidLinkMsg(LinkURL url, ErrorCode errorCode) {
        InvalidLinkMsg invalidLinkMsg = new InvalidLinkMsg();
        invalidLinkMsg.setMessage("invalid link");
        invalidLinkMsg.setPage(url);
        invalidLinkMsg.setErrorCode(errorCode);
        messageLogger.addMessage(invalidLinkMsg);
    }

    private void sendErrorMsg(LinkURL url, String error) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setMessage(error);
        errorMsg.setPage(url);
        messageLogger.addMessage(errorMsg);
    }

    private boolean isAllowedURL(LinkURL url) {
        //vraci true pokud se zacatek URL shoduje s nekterou z povolenych domen
        String link = url.getUrl();
        for (Domain allowedDomain : allowedDomains) {
            String domain = allowedDomain.getDomain();
            if (link.length() >= domain.length()) {
               if (domain.equals(link.substring(0,domain.length()))) {
                   return true;
               }
            }
        }
        return false;
    }

    @Override
    public void stopChecking() {
        stopped = true;
    }

    @Override
    public CrawlingState getCrawlingState() {
        return crawlingState;
    }

}
