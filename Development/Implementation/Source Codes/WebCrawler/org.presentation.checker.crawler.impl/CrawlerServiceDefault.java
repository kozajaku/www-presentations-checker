package WebCrawler.org.presentation.checker.crawler.impl;
import Model.org.presentation.checker.model.logging.MessageLogger;
import Model.org.presentation.checker.model.graph.TraversalGraph;
import WebCrawler.org.presentation.checker.crawler.CrawlerService;

/**
 * Default implementation of CrawlerService interface. This implementation will be
 * used if no service provider is registered into OSGi container.
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public class CrawlerServiceDefault implements CrawlerService {

	/**
	 * @author radio.koza
	 * @version 1.0
	 * @created 04-5-2014 20:56:23
	 */
	public class WebPage {

		private LinkURL linkURL;
		private int depthFromRoot;

		public WebPage(){

		}

		public void finalize() throws Throwable {

		}

		public List<WebPage> browseWebPage(){
			return null;
		}

		public boolean isOverMaximalDepth(){
			return false;
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
	private WebPage linkQueueQueue;
	private PageReceiver pageReceiver;
	private CrawlingState crawlingState;
	/**
	 * Initial 3000 ms timeout between requests.
	 */
	private int requestTimeout = 3000;

	public CrawlerServiceDefault(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * Private method which will be called probably in new thread from method
	 * startBrowsing.
	 */
	private void run(){

	}

	/**
	 * 
	 * @param url
	 * @param maximalDepth
	 * @param observer    Observer object, into which the CrawlerService
	 * implementation will send its results.
	 */
	public void startBrowsing(LinkURL url, int maximalDepth, PageCrawlingObserver observer){

	}

	/**
	 * 
	 * @param container
	 */
	public void offerMsgLoggerContainer(MessageLoggerContainer container){

	}

	/**
	 * 
	 * @param url
	 * @param maximalDepth
	 * @param observer    Observer object, into which the CrawlerService
	 * implementation will send its results.
	 * @param allowedDomains
	 * @param requestTimeout
	 */
	public void startBrowsing(LinkURL url, int maximalDepth, PageCrawlingObserver observer, List<Domain> allowedDomains, int requestTimeout){

	}

	public void stopChecking(){

	}

	public CrawlingState getCrawlingState(){
		return null;
	}

}