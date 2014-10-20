/*
inspired by http://www.byteslounge.com/tutorials/how-to-remove-included-resources-in-jsf-example 
*/
package org.presentation.presentation.helper;

/**
 *
 * @author petrof
 * 
 */

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

/**
 * This is the dirtiest class ever. It removes all the primefaces stylesheets.
 *
 * @author petrof
 * @version $Id: $Id
 */
public class RemoveResourcesListener implements SystemEventListener {

  /** {@inheritDoc} */
  @Override
  public void processEvent(SystemEvent event) throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();

	int i = context.getViewRoot().getComponentResources(context, "head").size() - 1;

	while (i >= 0) {
	    UIComponent resource = context.getViewRoot().getComponentResources(context, "head").get(i);
	    String resourceLibrary = (String) resource.getAttributes().get("library");
	    String resourceName = (String) resource.getAttributes().get("name");

	    if ("primefaces.css".equals(resourceName)) {
		context.getViewRoot().removeComponentResource(context, resource, "head");
	    }
	    
	    i--;
	}
    }

    /** {@inheritDoc} */
    @Override
    public boolean isListenerForSource(Object source) {
	return (source instanceof UIViewRoot);
    }
}
