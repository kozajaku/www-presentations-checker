package org.presentation.graphgenerator.impl;

import org.presentation.graphgenerator.GraphResult;
import org.presentation.model.graph.TraversalGraph;

/**
 * This class generates displayable form of traversal graph.
 *
 * @author Adam Kugler
 */
public abstract class GraphGenerator {

    /**
     * Generates displayable form of traversal graph from given graph.
     *
     * @param graph Graph that schould be processed
     * @return Graph in displayable form.
     */
    public abstract GraphResult generateGraphResult(TraversalGraph graph);
}
