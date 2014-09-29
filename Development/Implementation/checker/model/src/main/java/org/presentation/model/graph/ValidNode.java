package org.presentation.model.graph;

import java.util.ArrayList;
import java.util.List;
import org.presentation.model.LinkURL;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public class ValidNode extends Node {

	private List<Edge> orientedEdges;

	public ValidNode(LinkURL url) {
        this.url = url;
        this.inputDegree = 0;
        this.orientedEdges = new ArrayList<Edge>();
	}

	/**
	 * 
	 * @param edge
	 */
	public void addEdge(Edge edge){
            orientedEdges.add(edge);
	}

}