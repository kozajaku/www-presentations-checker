package org.presentation.utils;

/**
 * Interface for stopping the control. Class representing a control, that wants
 * to allow you to stop them, should implements this interface.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public interface Stoppable {

    /**
     * Stops the control.
     */
    public void stopChecking();
}
