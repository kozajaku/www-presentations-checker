package org.presentation.model.logging;

import org.presentation.utils.Property;

/**
 * Represents error type of abstract class
 * {@link org.presentation.model.logging.Message}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class ErrorMsg extends Message {

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefaultPriority() {
        return Property.getInstance().getIntProperty("ERROR_MESSAGE_PRIORITY");
    }

}
