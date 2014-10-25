package org.presentation.model.logging;

/**
 * Represents infomation type of abstract class Message.
 *
 * @author Jindřch Máca
 * @version 1.0-SNAPSHOT
 */
public class InfoMsg extends Message {

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefaultPriority() {
        return 2000;
    }

}
