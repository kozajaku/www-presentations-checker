package org.presentation.model.logging;

/**
 * Represents error type of abstract class Message.
 *
 * @author Jindřich Máca
 */
public class ErrorMsg extends Message {

    @Override
    public int getDefaultPriority() {
        return 4000;
    }

}
