package org.presentation.model.logging;

/**
 * Represents warning type of abstract class Message.
 *
 * @author Jindřich Máca
 */
public class WarningMsg extends Message {

    @Override
    public int getDefaultPriority() {
        return 3000;
    }

}
