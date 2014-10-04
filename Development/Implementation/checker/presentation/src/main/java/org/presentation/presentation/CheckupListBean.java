/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.presentation.persistence.model.Checkup;
import org.presentation.presentation.exception.UserAuthorizationException;

/**
 *
 * @author petrof
 */
@Named
@RequestScoped
public class CheckupListBean extends ProtectedBean {

    protected List<Checkup> checkupList;
    
    /**
     * Creates a new instance of CheckupListBean
     * @throws org.presentation.presentation.exception.UserAuthorizationException
     */
    public CheckupListBean() throws UserAuthorizationException {	
	super();
	checkupList = persistance.findUserCheckings(this.getLoggedUser());	
    }

    public List<Checkup> getCheckupList() {
	return checkupList;
	
	
	// todo find how to convert Enum to string i18n java
		
		// optionList
		// state
    }
                    
}
