/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.validation.constraints.NotNull;
import org.presentation.model.logging.Message;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;

/**
 *
 * @author petrof
 */
@Named
@RequestScoped
public class CheckupResultBean extends ProtectedBean {

    @NotNull
    protected int checkupId;
    
    protected Checkup checkup;
    

    public int getCheckupId() {
	return checkupId;
    }
    
    public void showResult() throws UserAuthorizationException{
	Checkup c = this.persistance.findCheckup(checkupId);		
	User user;
	
	this.checkup = c;
	
	if(c == null){
	    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_found")));
	    return;
	}
	
	user = c.getUser();	
	if(user == null || !user.equals(this.getLoggedUser())) {
	    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_yours")));
	    return;
	}				    
    }

    
    public List<Message> getMessages() throws UserAuthorizationException {
	return this.checkup == null ? null : this.persistance.findCheckupMessages(this.checkup);
    }

    public Checkup getCheckup() {
	return checkup;
    }

    public void setCheckupId(int checkupId) {
	this.checkupId = checkupId;
    }        
    
    
}
