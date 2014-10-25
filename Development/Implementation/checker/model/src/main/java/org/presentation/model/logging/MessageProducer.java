package org.presentation.model.logging;

/**
 * Interface of message producer, which associates all message loggers to one
 * container.
 *
 * @author Jindřich Máca
 * @version 1.0-SNAPSHOT
 */
public interface MessageProducer {

    /**
     * Offers message logger container to any class, which implements this
     * interface and calls this method.
     *
     * @param messageLoggerContainer Message logger container which associates
     * all message loggers.
     */
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer);
}
