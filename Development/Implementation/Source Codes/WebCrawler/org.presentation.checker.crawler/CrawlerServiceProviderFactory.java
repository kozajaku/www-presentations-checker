package WebCrawler.org.presentation.checker.crawler;

/**
 * Singleton factory class for receiving CrawlerService implementation.
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public class CrawlerServiceProviderFactory {

	private static CrawlerServiceProviderFactory instance;



	public void finalize() throws Throwable {

	}

	/**
	 * public constructor of this class is forbidden because the class should serve as
	 * singleton.
	 */
	private CrawlerServiceProviderFactory(){

	}

	/**
	 * Getter for CrawlerService interface concrete implementation.
	 */
	public CrawlerService getCrawlerServiceImpl(){
		return null;
	}

	/**
	 * Returns singleton instance of class AbstractClientServiceFactory
	 */
	public static CrawlerServiceProviderFactory getInstance(){
		return null;
	}

}