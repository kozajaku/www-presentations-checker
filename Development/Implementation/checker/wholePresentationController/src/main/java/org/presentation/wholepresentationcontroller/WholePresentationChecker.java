package org.presentation.wholepresentationcontroller;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.MessageProducer;
import org.presentation.parser.CSSCode;
import org.presentation.parser.HTMLCode;
import org.presentation.utils.Option;
import org.presentation.utils.Stoppable;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public interface WholePresentationChecker extends Stoppable, Option, MessageProducer {

    /**
     *
     * @param contentType
     * @param linkURL
     * @param pageContent
     * @param cssCode
     */
    public void addPage(ContentType contentType, LinkURL linkURL, PageContent pageContent, CSSCode cssCode);

    /**
     *
     * @param contentType
     * @param linkURL
     * @param pageContent
     * @param htmlCode
     */
    public void addPage(ContentType contentType, LinkURL linkURL, PageContent pageContent, HTMLCode htmlCode);

    /**
     *
     * @param traversalGraph
     */
    public void finalizeCheckup(TraversalGraph traversalGraph);

}
