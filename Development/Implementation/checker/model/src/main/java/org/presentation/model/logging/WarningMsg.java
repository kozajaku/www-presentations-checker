package org.presentation.model.logging;

/**
 * Represents warning type of abstract class Message.
 *
 * @author Jindřich Máca
 * @version 1.0-SNAPSHOT
 */
public class WarningMsg extends Message {

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefaultPriority() {
        return 3000;
    }

}
