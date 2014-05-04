package Presentation.org.presentation.checker.presentation;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:24
 */
public abstract class GraphGenerator {

	public GraphGenerator(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param traversalGraph
	 */
	public abstract GraphResult generateGraphResult(TraversalGraph traversalGraph);

}