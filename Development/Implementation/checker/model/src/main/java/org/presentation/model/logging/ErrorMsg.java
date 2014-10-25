package org.presentation.model.logging;

/**
 * Represents error type of abstract class Message.
 *
 * @author Jindřich Máca
 * @version 1.0-SNAPSHOT
 */
public class ErrorMsg extends Message {

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefaultPriority() {
        return 4000;
    }

}
