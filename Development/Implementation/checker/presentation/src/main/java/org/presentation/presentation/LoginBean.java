/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.presentation.persistence.model.User;
import org.presentation.presentation.validation.ValidEmail;
import org.presentation.presentation.validation.ValidPassword;


/**
 *
 * @author petrof
 */
@Named
@RequestScoped
public class LoginBean extends CommonBean {
        
    @NotNull 
    @ValidEmail
    private String email;
    
    @NotNull
    @ValidPassword
    private String password;
    
   
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        
    }
    
    public String login() throws ServletException, Exception{
	
	FacesContext context = FacesContext.getCurrentInstance();

	HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	
	try {
	    
	    User user = persistance.findUser(email);
	    if(user == null) throw new Exception(msg.getString("login.user_not_found"));
	    
	    request.login(email, password);
	    	    
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
	} catch (ServletException e) {	
	    context.addMessage(null, new FacesMessage(msg.getString("login.login_fail_message")));
	    return "";
	}
	
	return "protected/user/index";
    }
    
    public String logout() {
	FacesContext context = FacesContext.getCurrentInstance();
	HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	try {
	    request.logout();
	} catch (ServletException e) {
	    context.addMessage(null, new FacesMessage(msg.getString("login.logout_fail_message")));
	    return "";
	}
	return "public/index";
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
    
}
