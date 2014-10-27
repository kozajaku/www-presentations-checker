package org.presentation.model.graph;

/**
 * This class represents graph of links which schould be created in phase of
 * crawling target web presentation. Graph shows link connectivity of web
 * presentation.
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
     * Constructs link graph with root
     * {@link org.presentation.model.graph.Node}.
     *
     * @param root First {@link org.presentation.model.graph.Node} in link graph
     */
    public TraversalGraph(Node root) {
        this.root = root;
    }

    /**
     * <p>
     * Getter for the field <code>root</code>.</p>
     *
     * @return a {@link org.presentation.model.graph.Node} object.
     */
    public Node getRoot() {
        return root;
    }

}
