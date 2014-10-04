/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.presentation.persistence.business.PersistenceFacade;

/**
 *
 * @author petrof
 */
@RequestScoped
public abstract class CommonBean {   
    
    @EJB
    protected PersistenceFacade persistance;
    
    protected ResourceBundle msg;
    

    public CommonBean() {
	Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	msg = ResourceBundle.getBundle("org.presentation.presentation.lang", locale);

    }        
    
    protected void addMessage(FacesMessage msg){
	FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
