/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.validation.constraints.NotNull;
import org.presentation.kernel.CheckRequestReceiver;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.User;

/**
 * This bean is used to perform operations/actions on checkups
 *
 * @author petrof
 * @version $Id: $Id
 */
@Named
@RequestScoped
public class CheckupManipulatorBean extends ProtectedBean {

    @NotNull
    protected int checkupId;
    
    // todo
    
    @EJB
    protected CheckRequestReceiver checkRequestReceiver;
    
    /**
     * This action stops specific checkup
     *
     * @return jsf view
     * @throws java.lang.Exception if any.
     */
    public String stopCheckup() throws Exception{
	Checkup checkup = persistance.findCheckup(checkupId);
	User user = null;	
	
	if(checkup == null){
	    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_found")));
	    return "";
	}
	
	user = checkup.getUser();	
	if(user == null || !user.equals(this.getLoggedUser())) {
	    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_yours")));
	    return "";
	}
	
	if(checkup.getState().isEnded()) {
	    this.addMessage(new FacesMessage(msg.getString("checkupManipulator.checkup_already_stopped")));
	    return "";
	}
	
	checkRequestReceiver.stopSpecificChecking(checkup.getIdCheckup());
		
	this.addMessage(new FacesMessage(msg.getString("checkupManipulator.stopping_checkup_successful")));
	return "";
    }

    /**
     * <p>Getter for the field <code>checkupId</code>.</p>
     *
     * @return a int.
     */
    public int getCheckupId() {
	return checkupId;
    }

    /**
     * <p>Setter for the field <code>checkupId</code>.</p>
     *
     * @param checkupId a int.
     */
    public void setCheckupId(int checkupId) {
	this.checkupId = checkupId;
    }
    
    
    
}
