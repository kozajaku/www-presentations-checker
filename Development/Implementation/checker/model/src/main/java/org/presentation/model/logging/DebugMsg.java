package org.presentation.model.logging;

/**
 *
 * @author Jindřich Máca
 */
public class DebugMsg extends Message {

    @Override
    public int getDefaultPriority() {
	return 1000;
    }

}
