package org.presentation.wholepresentationcontroller;

import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.MessageProducer;
import org.presentation.parser.AbstractCode;
import org.presentation.utils.Option;
import org.presentation.utils.Stoppable;

/**
 * This interface must implement every submodule of WholePresentationController.
 * Pages crawled by web crawler and traversal graph will be passed through this
 * interface.
 *
 * @author radio.koza
 * @version 1.0
 */
public interface WholePresentationChecker extends Stoppable, Option, MessageProducer {

    /**
     * Add new page to the submodule of WholePresentationController. This method
     * is always called in different thread from web crawler but not parallerly
     * to submodules itself. It is not necessary for submodules to be thread
     * safe.
     *
     * @param code {@link AbstractCode} representing new page
     */
    public void addPage(AbstractCode code);

    /**
     * This is final step - method is called when checkup crawling is done and
     * the collected information should be converted to results.
     *
     * @param traversalGraph {@link TraversakGraph} created by web crawler that
     * can be used as additional information for submodule of
     * WholePresentationController module
     */
    public void finalizeCheckup(TraversalGraph traversalGraph);

}
