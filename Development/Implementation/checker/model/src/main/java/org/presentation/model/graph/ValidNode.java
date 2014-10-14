package org.presentation.model.graph;

import java.util.ArrayList;
import java.util.List;
import org.presentation.model.LinkURL;

/**
 * This class represents valid node in link graph.
 * @author Adam Kugler
 * @version 1.0
 */
public class ValidNode extends Node {

    private final List<Edge> orientedEdges;

    /**
     *
     * @param url an absolute URL representing link
     */
    public ValidNode(LinkURL url) {
        this.url = url;
        this.inputDegree = 0;
        this.orientedEdges = new ArrayList<>();
    }

    /**
     * Adds new edge from this node.
     * @param edge new edge which goes from this node
     */
    public void addEdge(Edge edge) {
        orientedEdges.add(edge);
    }

    public List<Edge> getOrientedEdges() {
        return orientedEdges;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
