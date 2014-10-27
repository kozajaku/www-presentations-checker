package org.presentation.model.logging;

/**
 * Represents warning type of abstract class {@link Message}.
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
        return 3000;
    }

}
