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
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.Edge;
import org.presentation.model.graph.InvalidNode;
import org.presentation.model.graph.Node;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.graph.ValidNode;
import org.presentation.model.logging.ErrorCode;
import org.presentation.model.logging.ErrorMsg;
import org.presentation.model.logging.Message;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.webcrawler.CompleteCrawlingState;
import org.presentation.webcrawler.CrawlerService;
import org.presentation.webcrawler.CrawlingState;
import org.presentation.webcrawler.PageCrawlingObserver;
import org.presentation.webcrawler.PageReceiver;
import org.presentation.webcrawler.ReceiverResponse;

/**
 * Default implementation of CrawlerService interface.
 * @author Adam Kugler
 * @version 1.0
 */
@Default
@Dependent
public class CrawlerServiceDefault implements CrawlerService {

	public class WebPage {
                private String label;
                private LinkSourceType linkSourceType;
                private ValidNode previousNode;
		private LinkURL linkURL;
		private int depthFromRoot;

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
                
		public List<WebPage> browseWebPage(){
                    List<WebPage> foundPages = new ArrayList<>();
                    foundPages.clear();
                    //podminky zastaveni
                    if (isOverMaximalDepth() || !isAllowedURL(linkURL) || pageCounter > pageLimit) return foundPages;
                    //stahni stranku
                    crawlingState.incCount();
                    ReceiverResponse receiverResponse;
                    try {
                        receiverResponse = pageReceiver.getPage(linkURL);
                    } catch (IOException ex) {
                        Logger.getLogger(CrawlerServiceDefault.class.getName()).log(Level.SEVERE, null, ex);
                        //posli zpravu o chybe
                        return foundPages; 
                    }
                    //vytvor node
                    if (receiverResponse.getStateCode() == 200) {
                        ValidNode node = new ValidNode(linkURL);
                        //spoj s grafem
                        if (previousNode != null) previousNode.addEdge(new Edge(node, label, linkSourceType));
                        visitedURLs.put(linkURL, node);
                        //najdi dalsi odkazy
                        List<ParsedLinkResponse> foundLinks;
                        foundLinks = getLinksFromPage(receiverResponse);
                        //over jestli stranka existuje
                        for (ParsedLinkResponse foundLink : foundLinks) {
                            LinkURL foundURL = foundLink.getDestination(); 
                            if (visitedURLs.containsKey(foundURL)) {
                                //pridej hranu
                                Edge newEdge = new Edge(visitedURLs.get(foundURL), foundLink.label, foundLink.sourceType);
                                node.addEdge(newEdge);
                            } else {
                                //pridej do fronty
                                foundPages.add(new WebPage(foundLink.label, foundLink.sourceType, node, foundURL, depthFromRoot+1));
                            }
                        }
                    } else {
                        InvalidNode node = new InvalidNode(linkURL, new ErrorCode(receiverResponse.getStateCode()));
                        //spoj s grafem
                        if (previousNode != null) previousNode.addEdge(new Edge(node, label, linkSourceType));
                    }
                    return foundPages;
               	}

		public boolean isOverMaximalDepth(){
		    if (depthFromRoot > maximalDepth) return false;
                    return true;
		}

	}

	/**
	 * This object is instantiated by registering into MessageLoggerContainer.
	 */
	private MessageLogger messageLogger;
	private PageCrawlingObserver observer;
	private TraversalGraph graph;
	private Domain allowedDomainsList;
	private int maximalDepth;
	private Queue<WebPage> linkQueue;
	private PageReceiver pageReceiver;
	private CrawlingState crawlingState;
        private CSSParserService cssParserService;
        private HTMLParserService htmlParserService;
	/**
	 * Initial 3000 ms timeout between requests.
	 */
	private int requestTimeout;
        private int pageLimit;
        private int pageCounter;
        
        private Map<LinkURL, Node> visitedURLs;

	/**
	 * 
	 * @param container
	 */
        @Override
	public void offerMsgLoggerContainer(MessageLoggerContainer container){
            messageLogger = container.createLogger("Web crawler");
	}

	/**
	 * 
	 * @param url
	 * @param maximalDepth
	 * @param observer    Observer object, into which the CrawlerService
	 * implementation will send its results.
	 * @param allowedDomains
	 */
        @Override
	public void startBrowsing(LinkURL url, int maximalDepth, int pageLimit, PageCrawlingObserver observer, List<Domain> allowedDomains, List<Header> addHeaders){
            //TODO add support for pageLimit and headers - by radio.koza
            this.pageLimit = pageLimit;
            this.requestTimeout = 3000;
            this.maximalDepth = maximalDepth;
            visitedURLs = new HashMap<>();
            linkQueue = new LinkedList<>();
            WebPage page = new WebPage(null, null, null, url, 0);
            linkQueue.add(page);
            while (!linkQueue.isEmpty()) {
               page = linkQueue.poll();
               linkQueue.addAll(page.browseWebPage());
            }
            crawlingState.done();
            observer.crawlingDone(graph, CompleteCrawlingState.ENDED_BY_DEPTH);
	}
        
        //dostan vsechny odkazy ze stranky
        private List<ParsedLinkResponse> getLinksFromPage(ReceiverResponse response) {
            if (response.getContentType().getContentType().equals("text/css")) return cssParserService.parseLinks(response.getSourceCode());
            else return htmlParserService.parseLinks(response.getSourceCode());
        }
        
        private boolean isAllowedURL(LinkURL url) {
           //TO DO
          return true;
        }

        @Override
	public void stopChecking(){

	}

        @Override
	public CrawlingState getCrawlingState(){
		return crawlingState;
	}

}