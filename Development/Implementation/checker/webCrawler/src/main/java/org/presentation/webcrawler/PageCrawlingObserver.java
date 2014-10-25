package org.presentation.webcrawler;

import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * <p>
 * PageCrawlingObserver interface.</p>
 *
 * @author Adam Kugler
 * @version 1.0
 */
public interface PageCrawlingObserver {

    /**
     * This method schould be called when crawled finds new page to process.
     *
     * @param pageUrl an absolute URL of page
     * @param pageSourceCode Source code of the received document
     * @param contentType For example: text/css, text/html, ...
     */
    public abstract void processOnePage(LinkURL pageUrl, PageContent pageSourceCode, ContentType contentType);

    /**
     * This method schould be called when web crawler is done with crawling.
     *
     * @param traversalGraph Graph created by crawler
     * @param crawlingState State of the finished web crawler (error occured,
     * ended by page depth, ...)
     */
    public abstract void crawlingDone(TraversalGraph traversalGraph, CompleteCrawlingState crawlingState);

}
