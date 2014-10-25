package org.presentation.model.graph;

/**
 * This class represents egde in
 * {@link org.presentation.model.graph.TraversalGraph}.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public class Edge {

    private final Node node;
    private final String name;
    private final LinkSourceType sourceType;
    private final boolean treeEdge;

    /**
     * Creates new edge which can be added to
     * {@link org.presentation.model.graph.ValidNode} from that it goes.
     *
     * @param node The graph node in direction of edge
     * @param name The label of edge
     * @param sourceType The source type of link
     * @param treeEdge <code>true</code> if edge is in crawling tree (node is
     * found as new)
     * @see ValidNode#addEdge(org.presentation.model.graph.Edge)
     */
    public Edge(Node node, String name, LinkSourceType sourceType, boolean treeEdge) {
        this.node = node;
        this.node.incInputDegree(); //zvys vstupni stupen
        this.name = name;
        this.sourceType = sourceType;
        this.treeEdge = treeEdge;
    }

    /**
     * <p>
     * Getter for the field <code>node</code>.</p>
     *
     * @return a {@link org.presentation.model.graph.Node} object.
     */
    public Node getNode() {
        return node;
    }

    /**
     * <p>
     * Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Getter for the field <code>sourceType</code>.</p>
     *
     * @return a {@link org.presentation.model.graph.LinkSourceType} object.
     */
    public LinkSourceType getSourceType() {
        return sourceType;
    }

    /**
     * <p>
     * isTreeEdge.</p>
     *
     * @return a boolean.
     */
    public boolean isTreeEdge() {
        return treeEdge;
    }

}
