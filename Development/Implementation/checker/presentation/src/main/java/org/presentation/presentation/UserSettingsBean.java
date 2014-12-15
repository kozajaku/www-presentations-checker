/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.presentation.presentation.validation.ValidPassword;

/**
 *
 * @author petrof
 */

@Named
@RequestScoped
public class UserSettingsBean extends ProtectedBean {
 
    @NotNull
    @ValidPassword
    private String password = "";

    @NotNull
    @ValidPassword
    private String passwordVerification = "";

    @Size(min = 2)
    @NotNull
    private String name = "";

    @Size(min = 2)
    @NotNull
    private String surname = "";
    
    private User user = null;
        
    protected void loadUser() throws UserAuthorizationException {
	if(user == null) {
	    user = this.getLoggedUser();
	}	
    }

    public String getPassword() {
	return "";
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getPasswordVerification() {
	return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
	this.passwordVerification = passwordVerification;
    }

    public String getName() throws UserAuthorizationException {
	loadUser();	
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSurname() throws UserAuthorizationException {
	loadUser();
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }
    
    public String updateSettings() {
	user.setName(name);
	user.setSurname(surname);
	this.persistance.editUser(user);
	return "userSettingsResult?type=settingsChange&faces-redirect=true";
    }
    
    public String changePassword() {
	
	return "userSettingsResult?type=passwordChange&faces-redirect=true";
    }
    
    
}
