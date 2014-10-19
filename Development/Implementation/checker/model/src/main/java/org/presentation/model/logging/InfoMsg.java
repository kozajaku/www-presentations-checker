package org.presentation.model.logging;

/**
 * Represents infomation type of abstract class Message.
 *
 * @author Jindřch Máca
 */
public class InfoMsg extends Message {

    @Override
    public int getDefaultPriority() {
        return 2000;
    }

}
