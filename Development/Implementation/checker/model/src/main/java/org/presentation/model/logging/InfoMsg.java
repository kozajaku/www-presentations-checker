package org.presentation.model.logging;

/**
 * Represents infomation type of abstract class {@link Message}.
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
        return 2000;
    }

}
