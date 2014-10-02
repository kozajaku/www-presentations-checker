package org.presentation.webcrawler.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
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
                private Node previousNode;
		private LinkURL linkURL;
		private int depthFromRoot;

                public WebPage(Node previousNode, LinkURL linkURL, int depthFromRoot) {
                    this.previousNode = previousNode;
                    this.linkURL = linkURL;
                    this.depthFromRoot = depthFromRoot;
                }

                public Node getPreviousNode() {
                    return previousNode;
                }

                public LinkURL getLinkURL() {
                    return linkURL;
                }
                
		public List<WebPage> browseWebPage(){
                    List<WebPage> foundPages = new ArrayList<>();
                    foundPages.clear();
                    //podminky zastaveni
                    if (isOverMaximalDepth() || !isAllowedURL(linkURL) || pageCounter > pageLimit) return foundPages;
                    //stahni stranku
                    ReceiverResponse receiverResponse;
                    Node node;
                    try {
                        receiverResponse = pageReceiver.getPage(linkURL);
                    } catch (IOException ex) {
                        Logger.getLogger(CrawlerServiceDefault.class.getName()).log(Level.SEVERE, null, ex);
                        //posli zpravu o chybe
                        return foundPages; 
                    }
                    //vytvor node
                    if (receiverResponse.getStateCode() == 200) {
                        node = new ValidNode(linkURL);
                    } else {
                        node = new InvalidNode(linkURL, new ErrorCode(receiverResponse.getStateCode()));
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
	private WebPage linkQueue;
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
            /*
            for (int i = 0; i < maximalDepth; i++) {
                pageReceiver.getPage(url);
            }    
            */
            if (isAllowedURL(url)) CrawlPage(url);
            
            observer.crawlingDone(graph, CompleteCrawlingState.ENDED_BY_DEPTH);
	}
        private Node CreateNodeFromPage(LinkURL url) {
            ReceiverResponse receiverResponse;
            Node node;
            try {
                receiverResponse = pageReceiver.checkPage(url);
            } catch (IOException ex) {
                Logger.getLogger(CrawlerServiceDefault.class.getName()).log(Level.SEVERE, null, ex);
                Message message = new ErrorMsg();
                message.setMessage("pageReceiver bad check");
                message.setPage(url);
                messageLogger.addMessage(message);
                return null;
            }
            if (receiverResponse.getStateCode() == 200) {
                node = new ValidNode(url);
            } else {
                node = new InvalidNode(url, new ErrorCode(receiverResponse.getStateCode()));
            }
           return node;       
        }
        
        //dostan vsechny odkazy ze stranky
        private List<ParsedLinkResponse> crawlPage(ReceiverResponse response) {
            if (response.getContentType() == "text/css") return cssParserService.parseLinks(response.getSourceCode());
            else return htmlParserService.parseLinks(response.getSourceCode());
        }
        
        private boolean isAllowedURL(LinkURL url) {
           //TO DO
          return true;
        }
        }
        @Override
	public void stopChecking(){

	}

	public CrawlingState getCrawlingState(){
		return null;
	}

}