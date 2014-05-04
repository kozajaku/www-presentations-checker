package Model.org.presentation.checker.model.logging;

/**
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class MessageLogger {

	/**
	 * Must be synchronized.
	 */
	private Message messagesList;
	/**
	 * Is final.
	 */
	private String resource;
	private boolean writable;

	public MessageLogger(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param resource
	 */
	MessageLogger(String resource){

	}

	/**
	 * 
	 * @param message
	 */
	public boolean addMessage(Message message){
		return false;
	}

	public MsgReport generateMsgReport(){
		return null;
	}

}