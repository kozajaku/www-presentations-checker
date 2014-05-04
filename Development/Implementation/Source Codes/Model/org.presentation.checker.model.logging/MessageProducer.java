package Model.org.presentation.checker.model.logging;

/**
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public interface MessageProducer {

	/**
	 * 
	 * @param container
	 */
	public abstract void offerMsgLoggerContainer(MessageLoggerContainer container);

}