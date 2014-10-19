package org.presentation.model.logging;

/**
 * Represents debug type of abstract class Message.
 *
 * @author Jindřich Máca
 */
public class DebugMsg extends Message {

    @Override
    public int getDefaultPriority() {
        return 1000;
    }

}
