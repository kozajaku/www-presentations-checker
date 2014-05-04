package Model.org.presentation.checker.model.graph;
import Model.org.presentation.checker.model.logging.ErrorCode;

/**
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class InvalidNode extends Node {

	/**
	 * Reason why the link is invalid.
	 */
	private ErrorCode errorCode;

	public InvalidNode(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param errorCode
	 */
	public InvalidNode(int errorCode){

	}

}