package org.presentation.model.logging;

/**
 *
 * @author Jindřich Máca
 */
public interface MessageProducer {

    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer);
}
