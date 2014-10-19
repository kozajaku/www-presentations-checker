/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.ArrayList;
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
 * This bean supports showing of the Results
 * 
 * @author petrof
 */
@Named
@RequestScoped
public class CheckupResultBean extends ProtectedBean {

    protected int checkupId = 0;
    
    protected List<String> messageResourcesAvailable;
    protected List<String> messageDiscriminatorsAvailable;
    protected String[] messageResourcesAllowed;
    protected String[] messageDiscriminatorsAllowed;
    
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
	
	// todo retrieve it dynamically
	this.messageDiscriminatorsAvailable = new ArrayList<>();
	this.messageDiscriminatorsAvailable.add("org.presentation.model.logging.DebugMsg");
	this.messageDiscriminatorsAvailable.add("org.presentation.model.logging.InfoMsg");
	this.messageDiscriminatorsAvailable.add("org.presentation.model.logging.WarningMsg");
	this.messageDiscriminatorsAvailable.add("org.presentation.model.logging.ErrorMsg");
    }
    
    public int getCheckupId() {
	return checkupId;
    }          
    
    /**
     * This action performs show-result-related operations
     * 
     * @throws UserAuthorizationException 
     */
    public void showResult() throws UserAuthorizationException {	
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

    /**
     * This getter gets the messages by filter selected
     * @return
     * @throws UserAuthorizationException 
     */
    public List<Message> getMessages() throws UserAuthorizationException {
	if(this.checkup == null) return null;
	
	boolean filterByResources = this.isFilteredByResources();
	boolean filterByDiscriminators = this.isFilteredByDiscriminators();
		
	if(!filterByResources && !filterByDiscriminators) {
	    return this.persistance.findCheckupMessages(checkup);
	} else {
	    if(filterByResources && filterByDiscriminators) {
		return this.persistance.findCheckupMessagesWithResourcesDiscriminators(checkup, Arrays.asList(this.messageResourcesAllowed), Arrays.asList(this.messageDiscriminatorsAllowed));
	    } else {
		if(filterByResources) {
		    return this.persistance.findCheckupMessagesWithResources(checkup, Arrays.asList(this.messageResourcesAllowed));
		} else if(filterByDiscriminators) {
		    return this.persistance.findCheckupMessagesWithDiscriminators(checkup, Arrays.asList(this.messageDiscriminatorsAllowed));
		}
	    }
	}
	    
	return null;
    }
    
    
    public boolean isFilteredByResources() {
	return (this.messageResourcesAllowed != null && this.messageResourcesAllowed.length > 0);
    }
    
    public boolean isFilteredByDiscriminators(){
	return (this.messageDiscriminatorsAllowed != null && this.messageDiscriminatorsAllowed.length > 0);
    }
    

    public Checkup getCheckup() {
	return checkup;
    }

    public void setCheckupId(int checkupId) {
	this.checkupId = checkupId;
    }        

    /**
     * This getter retrieves all available resources per checkup. i18n ready
     * @return all available message resources
     */
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
    
    /**
     * This getter retrieves all available discriminators. i18n ready
     * @return all available message discriminators
     */    
    public Map<String,Object> getMessageDiscriminatorsAvailable() {
	Map<String,Object> discriminatorsAvailable = new HashMap<>();
	
	if(this.messageDiscriminatorsAvailable != null) {
	    for(String s : this.messageDiscriminatorsAvailable) {
		String[] classPathParts = s.split("\\.");
		String className = classPathParts[classPathParts.length - 1];
		String discriminatorCaption;
		try {
		    discriminatorCaption = msg.getString("common.msg_type_" + className.toLowerCase());
		}
		catch(MissingResourceException e) {
		    discriminatorCaption = className;
		}
		discriminatorsAvailable.put(discriminatorCaption, s);
	    }
	}
	
	return discriminatorsAvailable;
    }

    public String[] getMessageResourcesAllowed() {
	return messageResourcesAllowed;
    }

    public void setMessageResourcesAllowed(String[] messageResourcesAllowed) {
	this.messageResourcesAllowed = messageResourcesAllowed;
    }

    public String[] getMessageDiscriminatorsAllowed() {
	return messageDiscriminatorsAllowed;
    }

    public void setMessageDiscriminatorsAllowed(String[] messageDiscriminatorsAllowed) {
	this.messageDiscriminatorsAllowed = messageDiscriminatorsAllowed;
    }
    
    
    
    
}
