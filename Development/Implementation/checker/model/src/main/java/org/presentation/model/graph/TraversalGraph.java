package org.presentation.model.graph;

//import org.presentation.model.LinkURL;
/**
 * @author Adam Kugler
 * @version 1.0
 */
public class TraversalGraph {

    /**
     * Is final.
     */
    private final Node root;

    /**
     *
     * @param root
     */
    public TraversalGraph(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }
    
}
