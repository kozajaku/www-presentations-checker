package org.presentation.model.logging;

import org.presentation.utils.Property;

/**
 * Represents warning type of abstract class
 * {@link org.presentation.model.logging.Message}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class WarningMsg extends Message {

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefaultPriority() {
        return Property.getInstance().getIntProperty("WARNING_MESSAGE_PRIORITY");
    }

}
