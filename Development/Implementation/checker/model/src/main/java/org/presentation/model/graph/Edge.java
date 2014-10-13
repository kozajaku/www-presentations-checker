package org.presentation.model.graph;

/**
 * This class represents egde in link graph.
 * @author Adam Kugler
 * @version 1.0
 */
public class Edge {

    private final Node node;
    private final String name;
    private final LinkSourceType sourceType;
    private final boolean treeEdge;

    /**
     * 
     * @param node the graph node in direction of edge
     * @param name the label of edge
     * @param sourceType the source type of link
     * @param treeEdge true if edge is in crawling tree (node is found as new)
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
