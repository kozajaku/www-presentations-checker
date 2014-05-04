package Model.org.presentation.checker.model.logging;
import Model.org.presentation.checker.model.LinkURL;

/**
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public abstract class Message {

	private String message;
	private LinkURL page;
	/**
	 * Can be null.
	 */
	private MsgLocation msgLocation;

	public Message(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param msgCounter
	 */
	public abstract void reportVisit(MsgReport msgCounter);

}