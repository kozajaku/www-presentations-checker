package org.presentation.model.logging;

import org.presentation.utils.Property;

/**
 * Represents infomation type of abstract class
 * {@link org.presentation.model.logging.Message}.
 *
 * @author Jindřch Máca
 * @version $Id: $Id
 */
public class InfoMsg extends Message {

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefaultPriority() {
        return Property.getInstance().getIntProperty("INFO_MESSAGE_PRIORITY");
    }

}
