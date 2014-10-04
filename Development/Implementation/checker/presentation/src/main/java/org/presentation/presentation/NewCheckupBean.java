/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.presentation.kernel.CheckRequestReceiver;
import org.presentation.kernel.CheckingRequest;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.presentation.validation.ValidUrl;
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
    @Min(1)
    @Max(10)
    protected int maxRequestFrequency = 3;
        
    @NotNull
    @Min(1)
    @Max(100000)
    protected int pageLimit = 0;
    
    protected List<Header> httpHeaders = new ArrayList<>();
    
    @EJB
    protected CheckRequestReceiver checkRequestReceiver;
    
    // 4 testing
      private SelectItem[] checkBoxItems = {
    new SelectItem("a", "1"),
    new SelectItem("b", "2"),
    new SelectItem("c", "3"),
    new SelectItem("d", "4")
  };

    public SelectItem[] getCheckBoxItems() {
	return checkBoxItems;
    }


    // 4 testing
    //private final Map<String,Object> checkupsAvailable;
    public Map<String,Object> getCheckupsAvailable() {	
	Map<String,Object> checkupsAvailable = new HashMap<>();	    
	checkupsAvailable.put(msg.getString("common.ch_css_redundancy"), CHK_CSS_REDUNDANCY);
	checkupsAvailable.put(msg.getString("common.ch_css_validation"), CHK_CSS_VALIDATION);
	checkupsAvailable.put(msg.getString("common.ch_html_validation"), CHK_HTML_VALIDATION);
	checkupsAvailable.put(msg.getString("common.ch_dead_links"), CHK_DEAD_LINKS);
	return checkupsAvailable;
    }    
    
    /**
     * Creates a new instance of NewCheckupBean
     */
    public NewCheckupBean() {
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

    public int getMaxRequestFrequency() {
	return maxRequestFrequency;
    }

    public void setMaxRequestFrequency(int maxRequestFrequency) {
	this.maxRequestFrequency = maxRequestFrequency;
    }

    public List<Header> getHttpHeaders() {
	return httpHeaders;
    }

    public void setHttpHeaders(List<Header> httpHeaders) {
	this.httpHeaders = httpHeaders;
    }

    
    
    public String startValidation(){
	
	CheckingRequest r = new CheckingRequest();
	OptionContainer oc = new OptionContainer();
	

	// checkboxes - to be tested - definition by array should be perfect
	//oc.addOption("...");
	
	
	r.setAllowedDomains(domainsAllowed);
	r.setMaxDepth(maxCrawlingDepth);
	r.setRequestInterval((int) Math.round(((double)1 / maxRequestFrequency) * 1000));
	r.setPageLimit(pageLimit);
	r.setStartingPoint(new LinkURL(startingLink));
	r.setHeaders(httpHeaders);
	r.setChosenOptions(oc);
	
	checkRequestReceiver.addNewCheckingRequest(r);
	
	return "protected/user/newCheckupAccepted";
	
    }
    
}
