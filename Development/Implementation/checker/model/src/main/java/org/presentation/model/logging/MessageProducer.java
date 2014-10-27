package org.presentation.model.logging;

/**
 * Interface of {@link MessageProducer}, which every control, that wants to
 * produce {@link Message} for user output, must implement. Through this
 * interface are registred all {@link MessageLogger} to specific
 * {@link MessageLoggerContainer}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public interface MessageProducer {

    /**
     * Offers {@link MessageLoggerContainer} to any control, which implements
     * this interface and wants to register its {@link MessageLogger} to produce
     * {@link Message} for user output.
     *
     * @param messageLoggerContainer {@link MessageLoggerContainer} to which can
     * control register its own {@link MessageLogger}
     */
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer);
}
