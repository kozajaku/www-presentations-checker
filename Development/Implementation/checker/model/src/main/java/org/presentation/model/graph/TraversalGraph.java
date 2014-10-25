package org.presentation.model.graph;

/**
 * This class represents graph of links which schould be created by web crawler
 * ({@link CrawlerService}). Graph shows link connectivity of web presentation.
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
     * Constructs link graph with root {@link Node}.
     *
     * @param root First {@link Node} in link graph
     */
    public TraversalGraph(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

}
