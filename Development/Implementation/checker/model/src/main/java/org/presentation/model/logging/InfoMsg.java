package org.presentation.model.logging;

/**
 *
 * @author Jindřch Máca
 */
public class InfoMsg extends Message {

    @Override
    public int getDefaultPriority() {
	return 2000;
    }
    
    
}
