package org.presentation.wholepresentationcontroller;

import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.MessageProducer;
import org.presentation.parser.AbstractCode;
import org.presentation.utils.Option;
import org.presentation.utils.Stoppable;

/**
 * @author radio.koza
 * @version 1.0
 */
public interface WholePresentationChecker extends Stoppable, Option, MessageProducer {

    /**
     * 
     * @param code 
     */
    public void addPage(AbstractCode code);


    /**
     *
     * @param traversalGraph
     */
    public void finalizeCheckup(TraversalGraph traversalGraph);

}
