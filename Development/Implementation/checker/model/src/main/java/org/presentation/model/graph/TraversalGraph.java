package org.presentation.model.graph;

/**
 * This class represents graph of links. Which schould be created by web
 * crawler.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public class TraversalGraph {

    /**
     * The root of link graph.
     */
    private final Node root;

    /**
     *
     * @param root first node in link graph
     */
    public TraversalGraph(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

}
