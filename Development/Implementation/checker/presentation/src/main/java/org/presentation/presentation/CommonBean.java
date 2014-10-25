/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.presentation.persistence.business.PersistenceFacade;

/**
 * This bean is a SUPER-BEAN that provides basic functionality for all of its
 * children
 *
 * @author petrof
 * @version $Id: $Id
 */
public abstract class CommonBean {

    @EJB
    protected PersistenceFacade persistance;

    protected ResourceBundle msg;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    protected Logger LOG;

    @PostConstruct
    private void init() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        msg = ResourceBundle.getBundle("org.presentation.presentation.lang", locale);
    }

    /**
     * <p>
     * addMessage.</p>
     *
     * @param msg a {@link javax.faces.application.FacesMessage} object.
     */
    protected void addMessage(FacesMessage msg) {
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
