/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.presentation.kernel.CheckRequestReceiver;
import org.presentation.kernel.CheckingRequest;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.ChosenOption;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.presentation.presentation.validation.ValidUrl;
import org.presentation.utils.AllowOptionService;
import org.presentation.utils.OptionContainer;

/**
 *
 * @author petrof
 */
@Named
@RequestScoped
public class NewCheckupBean extends ProtectedBean  {

    // todo - may be unused
    protected static final String CHK_CSS_REDUNDANCY = "CSS_REDUNDANCY";
    protected static final String CHK_CSS_VALIDATION = "CSS_VALIDATION";
    protected static final String CHK_HTML_VALIDATION = "HTML_VALIDATION";
    protected static final String CHK_DEAD_LINKS = "DEAD_LINKS";
    
    @NotNull
    @ValidUrl
    protected String startingLink;
        
    protected String[] desiredCheckups;
        
    protected List<Domain> domainsAllowed = new ArrayList<>();
    
    @NotNull
    @Min(1)
    @Max(20)
    protected int maxCrawlingDepth = 5;
    
    @NotNull
    @Min(500)
    @Max(10000)
    protected int minRequestInterval = 2000;
        
    @NotNull
    @Min(1)
    @Max(100000)
    protected int pageLimit = 50;
    
    protected List<Header> httpHeaders = new ArrayList<>();
    
    @Min(0)
    protected int previousCheckupId = 0;
    
    
    @EJB
    protected CheckRequestReceiver checkRequestReceiver;        
    
    @Inject 
    @Any
    protected Instance<AllowOptionService> optionServicePrototype;

    public Map<String,Object> getCheckupsAvailable() {	
	Map<String,Object> checkupsAvailable = new HashMap<>();	    

	for (AllowOptionService i: optionServicePrototype){
            try {
		checkupsAvailable.put(msg.getString("common.ch_" + i.getID().trim().replaceAll(" ", "_").toLowerCase()), i.getID());
	    }
	    catch(MissingResourceException ex) {
		checkupsAvailable.put(i.getID(), i.getID());
	    }
        }

	return checkupsAvailable;
    }    
    

    public String getStartingLink() {
	return startingLink;	
    }

    public void setStartingLink(String startingLink) {
	this.startingLink = startingLink;
    }

    public String[] getDesiredCheckups() {
	return desiredCheckups;
    }

    public void setDesiredCheckups(String[] desiredCheckups) {
	this.desiredCheckups = desiredCheckups;
    }   

    public int getPageLimit() {
	return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
	this.pageLimit = pageLimit;
    }
    
    
    public List<Domain> getDomainsAllowed() {
	return domainsAllowed;
    }

    public void setDomainsAllowed(List<Domain> domainsAllowed) {
	this.domainsAllowed = domainsAllowed;
    }
    
    public int getMaxCrawlingDepth() {
	return maxCrawlingDepth;
    }

    public void setMaxCrawlingDepth(int maxCrawlingDepth) {
	this.maxCrawlingDepth = maxCrawlingDepth;
    }

    public int getMinRequestInterval() {
	return minRequestInterval;
    }

    public void setMinRequestInterval(int minRequestInterval) {
	this.minRequestInterval = minRequestInterval;
    }
    

    public List<Header> getHttpHeaders() {
	return httpHeaders;
    }

    public void setHttpHeaders(List<Header> httpHeaders) {
	this.httpHeaders = httpHeaders;
    }

    public int getPreviousCheckupId() {
	return previousCheckupId;
    }
    
    
    public void setPreviousCheckupId(int previousCheckupId) {
	this.previousCheckupId = previousCheckupId;
    }
    
    
    public String startValidation() throws UserAuthorizationException, MalformedURLException {
	
	CheckingRequest r = new CheckingRequest();
	OptionContainer oc = new OptionContainer();
	
	/*	
	if(this.desiredCheckups.length == 0) {
	    this.addMessage(new FacesMessage(this.msg.getString("newCheckup.no_test_selected")));
	    return "";
	}
	*/

	// checkboxes - to be tested - definition by array should be perfect
	for( String option : this.desiredCheckups ) oc.addOption(option);
	

	if(domainsAllowed.isEmpty()) {
	    URL url = new URL(startingLink);
	    domainsAllowed.add(new Domain(url.getHost()));
	}
	r.setAllowedDomains(domainsAllowed);
	
	r.setMaxDepth(maxCrawlingDepth);
	r.setRequestInterval(this.minRequestInterval);
	r.setPageLimit(pageLimit);
	r.setStartingPoint(new LinkURL(startingLink));
	r.setHeaders(httpHeaders);
	r.setChosenOptions(oc);
	
	checkRequestReceiver.addNewCheckingRequest(this.getLoggedUser().getEmail(), r);
	
	return "newCheckupAccepted?faces-redirect=true";
	
    }
    
    
    
    public void loadPreviousCheckup() throws UserAuthorizationException {
	if(this.previousCheckupId > 0) {
	    Checkup c = this.persistance.findCheckup(this.previousCheckupId);
	    if(c != null) {
		if(c.getUser() == null || !c.getUser().equals(this.getLoggedUser())) {
		    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_yours")));
		    return;
		}
		
		this.setStartingLink(c.getStartPoint());
		this.setPageLimit(c.getPageLimit());
		this.setMinRequestInterval(c.getCheckingInterval());
		this.setMaxCrawlingDepth(c.getMaxDepth());
		
		
		List<Domain> domainList = this.persistance.findCheckupDomains(c);
		if(domainList != null) this.setDomainsAllowed(domainList);
		
		List<Header> headerList = this.persistance.findCheckupHeaders(c);
		if(headerList != null) this.setHttpHeaders(headerList);
		
		List<String> desiredCheckupIdList = new ArrayList<>();
		List<ChosenOption> optionList = this.persistance.findCheckupOptions(c);		
		if(optionList != null) {
		    for(ChosenOption chosenOption : optionList) desiredCheckupIdList.add(chosenOption.getIdOption());
		    if(desiredCheckupIdList.size() > 0) this.setDesiredCheckups((String[]) desiredCheckupIdList.toArray(new String[0]));
		}
		
	    }
	}
    }
    
}
