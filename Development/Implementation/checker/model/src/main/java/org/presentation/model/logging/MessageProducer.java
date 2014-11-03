package org.presentation.model.logging;

/**
 * Interface of {@link org.presentation.model.logging.MessageProducer}, which
 * every control, that wants to produce
 * {@link org.presentation.model.logging.Message} for user output, must
 * implement. Through this interface are registred all
 * {@link org.presentation.model.logging.MessageLogger} to specific
 * {@link org.presentation.model.logging.MessageLoggerContainer}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public interface MessageProducer {

    /**
     * Offers {@link org.presentation.model.logging.MessageLoggerContainer} to
     * any control, which implements this interface and wants to register its
     * {@link org.presentation.model.logging.MessageLogger} to produce
     * {@link org.presentation.model.logging.Message} for user output.
     *
     * @param messageLoggerContainer
     * {@link org.presentation.model.logging.MessageLoggerContainer} to which
     * can control register its own
     * {@link org.presentation.model.logging.MessageLogger}
     */
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer);
}
