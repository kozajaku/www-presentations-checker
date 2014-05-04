package Kernel.org.presentation.checker.kernel.impl;
import WebCrawler.org.presentation.checker.crawler.PageCrawlingObserver;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public class CrawlingObserverDefaultImpl extends PageCrawlingObserver {

	private WholePresentationController wholePresentationController;
	private SinglePageController singlePageController;

	public CrawlingObserverDefaultImpl(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param singlePageController
	 * @param wholePresentationController
	 */
	public CrawlingObserverDefaultImpl(SinglePageController singlePageController, WholePresentationController wholePresentationController){

	}

}