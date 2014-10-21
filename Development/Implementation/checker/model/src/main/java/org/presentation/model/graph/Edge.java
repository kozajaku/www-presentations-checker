package org.presentation.model.graph;

/**
 * This class represents egde in {@link TraversalGraph}.
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
     * Creates new edge which can be added to {@link ValidNode} from that it goes.
     *
     * @param node The graph node in direction of edge
     * @param name The label of edge
     * @param sourceType The source type of link
     * @param treeEdge <code>true</code> if edge is in crawling tree (node is found as new)
     * @see ValidNode#addEdge(org.presentation.model.graph.Edge) 
     */
    public Edge(Node node, String name, LinkSourceType sourceType, boolean treeEdge) {
        this.node = node;
        this.node.incInputDegree(); //zvys vstupni stupen
        this.name = name;
        this.sourceType = sourceType;
        this.treeEdge = treeEdge;
    }

    public Node getNode() {
        return node;
    }

    public String getName() {
        return name;
    }

    public LinkSourceType getSourceType() {
        return sourceType;
    }

    public boolean isTreeEdge() {
        return treeEdge;
    }

}
