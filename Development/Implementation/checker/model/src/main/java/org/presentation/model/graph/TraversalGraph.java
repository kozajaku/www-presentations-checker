package org.presentation.model.graph;
import org.presentation.model.LinkURL;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public class TraversalGraph {

	/**
	 * Is final.
	 */
	private Node root;


	/**
	 * 
	 * @param root
	 */
	public TraversalGraph(Node root){
            this.root = root;
	}

//	/**
//	 * Returns if graph already contains target link.
//	 * 
//	 * @param destURL    Destination URL.
//	 * @param sourceURL
//	 * @param sourceType
//	 * @param stateCode
//	 */
//	public boolean addLink(LinkURL destURL, LinkURL sourceURL, LinkSourceType sourceType, int stateCode){
//		return false;
//	}

}