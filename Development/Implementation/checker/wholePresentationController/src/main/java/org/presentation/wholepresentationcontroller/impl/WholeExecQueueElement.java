package org.presentation.wholepresentationcontroller.impl;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;

/**
 * This class represents immutable holder which serves as element in the queue
 * in {@link AsyncWholeCheckerExecutor}. Either holder holds instances of
 * objects representing page itself (content, source link and content type), or
 * holder represents final element of the queue and holds TraversalGraph
 *
 * @author radio.koza
 */
public class WholeExecQueueElement {

    private PageContent pageContent;
    private LinkURL link;
    private ContentType contentType;
    private TraversalGraph traversalGraph;
    private boolean endFlag;

    /**
     * Constructor for creation of holder with set instances of classes
     * representing page.
     *
     * @param pageContent Source code of the page
     * @param link Source link of the page
     * @param contentType Content type of the page
     */
    public WholeExecQueueElement(PageContent pageContent, LinkURL link, ContentType contentType) {
        this.pageContent = pageContent;
        this.link = link;
        this.contentType = contentType;
    }

    /**
     * Constructor for setting the final flag to the element. Instances
     * representing page are set to <code>null</code> and traversal graph is set
     * to not null for this element.
     *
     * @param travGraph Traversal graph usable for final work of wpc. May be
     * <code>null</code> if not generated by web crawler.
     */
    public WholeExecQueueElement(TraversalGraph travGraph) {
        endFlag = true;
        this.traversalGraph = travGraph;
    }

    /**
     * Method informs if this is the last element of the queue and so the
     * executor thread should be prepared for final whole check.
     *
     * @return <code>true</code> if this is the last element of the queue;
     * <code>false</code> otherwise
     */
    public boolean isEndOfQueue() {
        return endFlag;
    }

    public PageContent getPageContent() {
        return pageContent;
    }

    public LinkURL getLink() {
        return link;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public TraversalGraph getTraversalGraph() {
        return traversalGraph;
    }

}