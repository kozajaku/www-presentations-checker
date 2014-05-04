package Model.org.presentation.checker.model.graph;
import Model.org.presentation.checker.model.LinkURL;

/**
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:26
 */
public class TraversalGraph {

	/**
	 * Is final.
	 */
	private Node root;

	public TraversalGraph(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param root
	 */
	public TraversalGraph(LinkURL root){

	}

	/**
	 * Returns if graph already contains target link.
	 * 
	 * @param destURL    Destination URL.
	 * @param sourceURL
	 * @param sourceType
	 * @param stateCode
	 */
	public boolean addLink(LinkURL destURL, LinkURL sourceURL, LinkSourceType sourceType, int stateCode){
		return false;
	}

}