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
 * This helper supports persisting GET parameters over POST form calls
 *
 * @author petrof
 * @version $Id: $Id
 */
public class CustomViewHandler extends ViewHandlerWrapper {

    private ViewHandler wrapped;

    /**
     * <p>
     * Constructor for CustomViewHandler.</p>
     *
     * @param wrapped a {@link javax.faces.application.ViewHandler} object.
     */
    public CustomViewHandler(ViewHandler wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewHandler getWrapped() {
        return wrapped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getActionURL(FacesContext context, String viewId) {
        return context.getExternalContext().encodeBookmarkableURL(
                super.getActionURL(context, viewId), getViewParameterMap(context));
    }

    /**
     * <p>
     * getViewParameters.</p>
     *
     * @param context a {@link javax.faces.context.FacesContext} object.
     * @return a {@link java.util.Collection} object.
     */
    public static Collection<UIViewParameter> getViewParameters(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        return (viewRoot != null) ? ViewMetadata.getViewParameters(viewRoot) : Collections.<UIViewParameter>emptyList();
    }

    /**
     * <p>
     * getViewParameterMap.</p>
     *
     * @param context a {@link javax.faces.context.FacesContext} object.
     * @return a {@link java.util.Map} object.
     */
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
