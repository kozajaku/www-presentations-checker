/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewMetadata;

/**
 *
 * @author petrof
 */
public class CustomViewHandler extends ViewHandlerWrapper {

    private ViewHandler wrapped;

    public CustomViewHandler(ViewHandler wrapped) {
        this.wrapped = wrapped;
    }
    
    @Override
    public ViewHandler getWrapped() {
	return wrapped;
    }

    @Override
    public String getActionURL(FacesContext context, String viewId) {
	return context.getExternalContext().encodeBookmarkableURL(
		super.getActionURL(context, viewId), getViewParameterMap(context));
    }
    
    
    public static Collection<UIViewParameter> getViewParameters(FacesContext context) {
	    UIViewRoot viewRoot = context.getViewRoot();
	    return (viewRoot != null) ? ViewMetadata.getViewParameters(viewRoot) : Collections.<UIViewParameter>emptyList();
    }
    
    public static Map<String, List<String>> getViewParameterMap(FacesContext context) {
	    Collection<UIViewParameter> viewParameters = getViewParameters(context);

	    if (viewParameters.isEmpty()) {
		    return Collections.<String, List<String>>emptyMap();
	    }

	    Map<String, List<String>> parameterMap = new HashMap<>();

	    for (UIViewParameter viewParameter : viewParameters) {
		    String value = viewParameter.getStringValue(context);

		    if (value != null) {
			    // <f:viewParam> doesn't support multiple values anyway, so having multiple <f:viewParam> on the
			    // same request parameter shouldn't end up in repeated parameters in action URL.
			    parameterMap.put(viewParameter.getName(), Collections.singletonList(value));
		    }
	    }

	    return parameterMap;
    }    
}
