/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.presentation.presentation.validation.ValidEmail;
import org.presentation.presentation.validation.ValidPassword;

/**
 *
 * @author petrof
 */
@Named
@Dependent
public class SignupBean extends CommonBean {
    
    @NotNull
    @ValidEmail
    public String email;
    
    @NotNull
    @ValidPassword
    public String password;
    
    @Size(min=2)
    @NotNull
    public String name;
    
    @Size(min=2)
    @NotNull    
    public String surname;
    
    /**
     * Creates a new instance of SignupBean
     */
    public SignupBean() {
	
    }
    
    public void signUp(){
		
	
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }
    
    
    
}
