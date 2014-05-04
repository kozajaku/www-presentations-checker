package Presentation.org.presentation.checker.presentation;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:24
 */
public class GraphvizExecutor {

	/**
	 * Singleton holder.
	 */
	private static GraphvizExecutor instance;



	public void finalize() throws Throwable {

	}

	/**
	 * Public constructor is forbidden.
	 */
	private GraphvizExecutor(){

	}

	public static GraphvizExecutor getInstance(){
		return null;
	}

	/**
	 * Returns SVG source.
	 * 
	 * @param graphvizSource
	 */
	public String executeGraphviz(String graphvizSource){
		return "";
	}

}