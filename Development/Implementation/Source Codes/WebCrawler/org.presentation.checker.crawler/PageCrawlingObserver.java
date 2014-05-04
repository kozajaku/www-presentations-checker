package WebCrawler.org.presentation.checker.crawler;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class PageCrawlingObserver {

	public PageCrawlingObserver(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param pageUrl
	 * @param pageSourceCode    Source code of the received document.
	 * @param contentType    For example: text/css, text/html, ...
	 */
	public abstract void processOnePage(LinkURL pageUrl, PageContent pageSourceCode, ContentType contentType);

	/**
	 * 
	 * @param traversalGraph
	 * @param crawlingState    State of the finished web crawler (error occured, ended
	 * by page depth, ...)
	 */
	public abstract void crawlingDone(TraversalGraph traversalGraph, CompleteCrawlingState crawlingState);

}