package org.presentation.model.graph;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public class Edge {

	private Node node;
	private String name;
	private LinkSourceType sourceType;

	public Edge(Node node, String name, LinkSourceType sourceType){
            this.node = node;
            this.node.incInputDegree(); //zvys vstupni stupen
            this.name = name;
            this.sourceType = sourceType;
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
        
        
}