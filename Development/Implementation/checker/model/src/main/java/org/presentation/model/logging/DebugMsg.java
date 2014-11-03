package org.presentation.model.logging;

/**
 * Represents debug type of abstract class
 * {@link org.presentation.model.logging.Message}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
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
