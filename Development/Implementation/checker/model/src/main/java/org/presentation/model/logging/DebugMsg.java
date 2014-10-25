package org.presentation.model.logging;

/**
 * Represents debug type of abstract class Message.
 *
 * @author Jindřich Máca
 * @version 1.0-SNAPSHOT
 */
public class DebugMsg extends Message {

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefaultPriority() {
        return 1000;
    }

}
