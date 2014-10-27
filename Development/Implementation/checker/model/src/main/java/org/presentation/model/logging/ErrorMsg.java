package org.presentation.model.logging;

/**
 * Represents error type of abstract class {@link Message}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class ErrorMsg extends Message {

    @Override
    public int getDefaultPriority() {
        return 4000;
    }

}
