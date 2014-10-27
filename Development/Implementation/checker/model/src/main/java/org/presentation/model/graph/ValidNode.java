package org.presentation.model.graph;

import java.util.ArrayList;
import java.util.List;
import org.presentation.model.LinkURL;

/**
 * This class represents valid node in
 * {@link org.presentation.model.graph.TraversalGraph}.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public class ValidNode extends Node {

    private final List<Edge> orientedEdges;

    /**
     * Creates valid node without sucessors.
     *
     * @param url An absolute URL representing web page
     */
    public ValidNode(LinkURL url) {
        this.url = url;
        this.inputDegree = 0;
        this.orientedEdges = new ArrayList<>();
    }

    /**
     * Adds new {@link org.presentation.model.graph.Edge} from this node.
     *
     * @param edge New {@link org.presentation.model.graph.Edge} which goes from
     * this node
     */
    public void addEdge(Edge edge) {
        orientedEdges.add(edge);
    }

    /**
     * <p>
     * Getter for the field <code>orientedEdges</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Edge> getOrientedEdges() {
        return orientedEdges;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return true;
    }
}
