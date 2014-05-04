package Model.org.presentation.checker.model.logging;

/**
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class MessageLoggerContainer {

	private MessageLogger loggersList;

	public MessageLoggerContainer(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param resource
	 */
	public MessageLogger createLogger(String resource){
		return null;
	}

	/**
	 * Brutal stop operation.
	 */
	public void shutDownLogging(){

	}

}