package WebCrawler.org.presentation.checker.crawler;
import Model.org.presentation.checker.model.LinkURL;
import Model.org.presentation.checker.model.logging.MessageProducer;
import Utils.org.presentation.checker.utils.Stoppable;

/**
 * Interface for WebCrawler service. Every module, that want to serve as
 * WebCrawler service provider must register implementation of this service to
 * OSGi container.
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public interface CrawlerService extends MessageProducer, Stoppable {

	/**
	 * 
	 * @param url
	 * @param maximalDepth
	 * @param observer    Observer object, into which the CrawlerService
	 * implementation will send its results.
	 */
	public abstract void startBrowsing(LinkURL url, int maximalDepth, PageCrawlingObserver observer);

	/**
	 * 
	 * @param url
	 * @param maximalDepth
	 * @param observer    Observer object, into which the CrawlerService
	 * implementation will send its results.
	 * @param allowedDomains
	 * @param requestTimeout
	 */
	public abstract void startBrowsing(LinkURL url, int maximalDepth, PageCrawlingObserver observer, List<Domain> allowedDomains, int requestTimeout);

	public CrawlingState getCrawlingState();

}