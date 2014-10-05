package org.presentation.presentation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import org.presentation.persistence.model.User;
import org.presentation.presentation.ProtectedBean;
import org.presentation.presentation.exception.UserAuthorizationException;

/**
 *
 * @author petrof
 */
@Named
@RequestScoped
public class HeaderBean extends ProtectedBean {
    
    public boolean getLoggedIn(){
	try {
	    return this.getLoggedUser() == null ? false : true;	    
	} catch (UserAuthorizationException ex) {
	    return false;
	}
    }
    
    public User getUser() throws UserAuthorizationException{
	try {
	    return this.getLoggedUser();
	} catch (UserAuthorizationException ex) {
	    return null;
	}
    }
    
}
