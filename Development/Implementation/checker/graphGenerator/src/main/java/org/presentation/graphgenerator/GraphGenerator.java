package org.presentation.graphgenerator;

import org.presentation.model.graph.TraversalGraph;

/**
 * This class generates displayable form of traversal graph.
 * @author Adam Kugler
 */
public abstract class GraphGenerator {
    /**
     * 
     * @param graph Graph that schould be processed
     * @return Graph in displayable form.
     */
    public abstract GraphResult generateGraphResult(TraversalGraph graph);
}
