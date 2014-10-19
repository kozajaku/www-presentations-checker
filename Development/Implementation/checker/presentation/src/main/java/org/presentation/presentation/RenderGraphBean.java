/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.Graph;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;

/**
 *
 * @author petrof
 */
@Named
@RequestScoped
public class RenderGraphBean extends ProtectedBean {
    
    @NotNull
    protected int checkupId;
    
    protected Checkup checkup;
         
    protected String graphType;
    
    protected Graph selectedGraph;
    
    
    @PostConstruct
    public void init() {
	if(checkupId == 0) {
	    Map<String, String> params =FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
	    String pCheckupId = params.get("checkupId");
	    if(pCheckupId != null) checkupId = Integer.parseInt(pCheckupId);
	}
    }       
    
    protected boolean loadCheckup() throws UserAuthorizationException{
	Checkup c = this.persistance.findCheckup(checkupId);		
	
	this.checkup = c;
	
	if(c == null){
	    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_found")));
	    return false;
	}
	
	User user = checkup.getUser();	
	if(user == null || !user.equals(this.getLoggedUser())) {
	    this.checkup = null;	
	    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_yours")));
	    return false;
	} else return true;		
    }
    
    protected boolean validateSpecificGraphIdVars(){
	if(this.checkup != null) {
	    if(graphType != null && this.graphType.length() > 0) {
		return true;
	    } else {
		this.addMessage(new FacesMessage(msg.getString("RenderGraph.graph_not_set")));
	    }
	}	
	return false;
    }
    
    public void showGraph() throws UserAuthorizationException {
	LOG.log(Level.INFO, "ACTION FIRED");
	if(!this.loadCheckup()) return;
	if(this.validateSpecificGraphIdVars()) {
	    this.selectedGraph = this.persistance.findGraphByGraphType(checkup, this.graphType);	
	}
    }
    
    public void showGraphList() throws UserAuthorizationException {
	if(!this.loadCheckup()) return;	
    }
    
    public void download() throws IOException, UserAuthorizationException {
	this.showGraph();
	if(this.selectedGraph != null) {
	
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();

	    ec.responseReset(); 
	    ec.setResponseContentType("application/octet-stream");
	    ec.setResponseContentLength(this.selectedGraph.getOutput().length()); 
	    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"graph" + this.selectedGraph.getIdGraph() + ".html\""); 

	    OutputStream output = ec.getResponseOutputStream();
	    output.write(this.selectedGraph.getOutput().getBytes());

	    fc.responseComplete(); 
	}
    }    
    
    public int getCheckupId() {
	return checkupId;
    }

    public void setCheckupId(int checkupId) {
	this.checkupId = checkupId;
    }

    public String getGraphType() {
	return graphType;
    }

    public void setGraphType(String graphType) {
	this.graphType = graphType;
    }

    public Graph getSelectedGraph() {
	return selectedGraph;
    }

    public Checkup getCheckup() {
	return checkup;
    }

    public List<Graph> getAvailableGraphs() throws UserAuthorizationException {
	if(this.checkup == null) this.loadCheckup();
	if(this.checkup != null) return this.persistance.findCheckupGraphs(checkup); else return null;
    }
        
    
    
    
}
