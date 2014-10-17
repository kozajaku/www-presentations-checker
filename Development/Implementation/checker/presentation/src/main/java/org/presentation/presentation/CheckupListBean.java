/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.ListDataModel;
import org.presentation.persistence.model.Checkup;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.presentation.presentation.helper.CheckupEnvelope;
import org.presentation.presentation.helper.DataListingSupport;

/**
 *
 * @author petrof
 */
@Named
@RequestScoped
public class CheckupListBean extends DataListingSupport<CheckupEnvelope> {
  
 
    public List<CheckupEnvelope> getCheckupList() {		
	List<Checkup> checkups;	
	List<CheckupEnvelope> checkupList = new ArrayList<>();
		
	try {			
	    checkups = persistance.findUserCheckings(this.getLoggedUser());
	
	    for(Checkup checkup: checkups) {
		checkup.setOptionList(persistance.findCheckupOptions(checkup));

		checkupList.add(new CheckupEnvelope(
		    checkup,
		    persistance.findCheckupDomains(checkup),
		    checkup.getOptionList()
		));
	    }

	} catch (UserAuthorizationException ex) {
	    //todo
	}		    
	    
	return checkupList;
	
    }                    

    @Override
    protected void populateCountAndData() {	
	this.setRecordCount(this.getCheckupList().size());
	this.setData(new ListDataModel<>(this.getCheckupList().subList((this.getPage()-1) * this.getRowsPerPage(), this.getPage() * this.getRowsPerPage())));	
    }
    
}
