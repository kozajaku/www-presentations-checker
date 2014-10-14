/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

    protected int checkupId = 0;
    
    protected List<String> messageResourcesAvailable;
    protected String[] messageResourcesAllowed;
    
    protected Checkup checkup;
    
    
    @PostConstruct
    public void init() {
	if(checkupId == 0) {
	    Map<String, String> params =FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
	    String pCheckupId = params.get("checkupId");
	    if(pCheckupId != null) checkupId = Integer.parseInt(pCheckupId);
	}
	
	Checkup c = this.persistance.findCheckup(checkupId);		
	
	this.checkup = c;
	
	if(c == null){
	    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_found")));
	    return;
	}
		
	this.messageResourcesAvailable = this.persistance.findCheckupMessageResources(c);	
    }
    
    public int getCheckupId() {
	return checkupId;
    }          
    
    
    public void showResult() throws UserAuthorizationException{	
	if(this.checkup != null) {
	    User user = this.checkup.getUser();	
	    if(user == null || !user.equals(this.getLoggedUser())) {
		this.addMessage(new FacesMessage(msg.getString("common.checkup_not_yours")));
		this.checkup = null;
		return;
	    }

	    if(this.messageResourcesAllowed != null && this.messageResourcesAllowed.length > 0 && !this.messageResourcesAvailable.containsAll(Arrays.asList(messageResourcesAllowed))) {
		this.messageResourcesAllowed = null;
		this.addMessage(new FacesMessage(msg.getString("checkupResult.resource_not_available")));
		return;	    
	    }		
	}
    }

    
    public List<Message> getMessages() throws UserAuthorizationException {
	if(this.checkup == null) return null;
	
	if(this.messageResourcesAllowed == null || this.messageResourcesAllowed.length == 0) {
	    return this.persistance.findCheckupMessages(this.checkup);
	} else {
	   // return this.checkup == null ? null : this.persistance.findCheckupMessages(this.checkup);
	    
	    // todo - fetch only checkups with desired resources
	    return this.persistance.findCheckupMessagesWithResource(checkup, this.messageResourcesAllowed[0]);
	}
    }

    public Checkup getCheckup() {
	return checkup;
    }

    public void setCheckupId(int checkupId) {
	this.checkupId = checkupId;
    }        

 
    public Map<String,Object> getMessageResourcesAvailable() {	
	Map<String,Object> resourcesAvailable = new HashMap<>();	    
	
	if(this.messageResourcesAvailable != null) {
	    for(String s : this.messageResourcesAvailable) {
		try {
		    resourcesAvailable.put(msg.getString("common.ch_" + s.toLowerCase()), s);
		}
		catch(MissingResourceException e) {
		    resourcesAvailable.put(s, s);
		}
	    }
	}	
	
	return resourcesAvailable;
    }    

    public String[] getMessageResourcesAllowed() {
	return messageResourcesAllowed;
    }

    public void setMessageResourcesAllowed(String[] messageResourcesAllowed) {
	this.messageResourcesAllowed = messageResourcesAllowed;
    }
    
    
}
