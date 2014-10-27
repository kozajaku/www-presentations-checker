package org.presentation.model.logging;

/**
 * Represents debug type of abstract class {@link Message}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class DebugMsg extends Message {

    @Override
    public int getDefaultPriority() {
        return 1000;
    }

}
