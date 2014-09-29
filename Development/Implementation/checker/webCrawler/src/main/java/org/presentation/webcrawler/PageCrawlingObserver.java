package org.presentation.webcrawler;

import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public interface PageCrawlingObserver{
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