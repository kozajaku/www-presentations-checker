package Presentation.org.presentation.checker.presentation;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:24
 */
public class GraphvizSourceGenerator {

	private static GraphvizSourceGenerator instance;



	public void finalize() throws Throwable {

	}

	/**
	 * Public constructor is forbidden.
	 */
	private GraphvizSourceGenerator(){

	}

	public static GraphvizSourceGenerator getInstance(){
		return null;
	}

	/**
	 * Returns graphviz source.
	 * 
	 * @param traversalGraph
	 */
	public String generateSource(TraversalGraph traversalGraph){
		return "";
	}

}