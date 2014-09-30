package org.presentation.webcrawler.impl;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.presentation.model.Domain;
import org.presentation.model.LinkURL;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.webcrawler.CompleteCrawlingState;
import org.presentation.webcrawler.CrawlerService;
import org.presentation.webcrawler.CrawlingState;
import org.presentation.webcrawler.PageCrawlingObserver;
import org.presentation.webcrawler.PageReceiver;

/**
 * Default implementation of CrawlerService interface.
 * @author Adam Kugler
 * @version 1.0
 */
public class CrawlerServiceDefault implements CrawlerService {

//	public class WebPage {
//
//		private LinkURL linkURL;
//		private int depthFromRoot;
//		public List<WebPage> browseWebPage(){
//			return null;
//		}
//
//		public boolean isOverMaximalDepth(){
//			return false;
//		}
//
//	}

	/**
	 * This object is instantiated by registering into MessageLoggerContainer.
	 */
	private MessageLogger messageLogger;
	private PageCrawlingObserver observer;
	private TraversalGraph graph;
	//private Domain allowedDomainsList;
	//private int maximalDepth;
	//private WebPage linkQueueQueue;
	private PageReceiver pageReceiver;
	private CrawlingState crawlingState;
	/**
	 * Initial 3000 ms timeout between requests.
	 */
	private int requestTimeout = 3000;
        private int pageLimit = 5000;

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
	public void startBrowsing(LinkURL url, int maximalDepth, PageCrawlingObserver observer, List<Domain> allowedDomains){
            
            try {
                pageReceiver.getPage(url);
            } catch (IOException ex) {
                Logger.getLogger(CrawlerServiceDefault.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            observer.crawlingDone(graph, CompleteCrawlingState.ENDED_BY_DEPTH);
	}

        @Override
	public void stopChecking(){

	}

	public CrawlingState getCrawlingState(){
		return null;
	}

}