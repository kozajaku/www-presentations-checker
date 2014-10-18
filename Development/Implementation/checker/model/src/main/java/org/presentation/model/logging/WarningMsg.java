package org.presentation.model.logging;

/**
 *
 * @author Jindřich Máca
 */
public class WarningMsg extends Message {

    @Override
    public int getDefaultPriority() {
	return 3000;
    }   
    
}
