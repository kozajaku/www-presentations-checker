/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.presentation.persistence.model.Checkup;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.presentation.presentation.helper.CheckupEnvelope;

/**
 *
 * @author petrof
 */
@Named
@RequestScoped
public class CheckupListBean extends ProtectedBean {
  
 
    public List<CheckupEnvelope> getCheckupList() throws UserAuthorizationException {		
	List<Checkup> checkups;	
	List<CheckupEnvelope> checkupList = new ArrayList<>();
		
	checkups = persistance.findUserCheckings(this.getLoggedUser());			
	
	for(Checkup checkup: checkups) {
	    checkup.setOptionList(persistance.findCheckupOptions(checkup));
	    
	    checkupList.add(new CheckupEnvelope(
		checkup,
		persistance.findCheckupDomains(checkup),
		checkup.getOptionList()
	    ));
	}
	
	return checkupList;
	
    }                    
    
}
