/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.presentation.persistence.model.User;
import org.presentation.presentation.validation.ValidEmail;


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
    //@ValidPassword
    private String password;
         
    public String login() throws ServletException, Exception{
	
	FacesContext context = FacesContext.getCurrentInstance();

	HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    
	User user = persistance.findUser(email);
	if(user == null) {
	    this.addMessage(new FacesMessage(msg.getString("login.user_not_found")));
	    return "";
	}
	
	
	try {	   	    
	    request.login(email, password);	    	    
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
	    	    
	} catch (ServletException e) {	
	    this.addMessage(new FacesMessage(msg.getString("login.login_fail_message")));
//            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, null, e);
	    return "";
	}
	
	return "/protected/user/index?faces-redirect=true";
    }
    
    public String logout() {
	FacesContext context = FacesContext.getCurrentInstance();
	HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	try {
	    request.logout();
	    context.getExternalContext().invalidateSession();
	    /*
	    Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();	   
	    if(sessionMap != null) {
		sessionMap.clear();
	    }*/
	} catch (ServletException e) {
	    this.addMessage(new FacesMessage(msg.getString("login.logout_fail_message")));
	    return "";
	}
	return "/public/index?faces-redirect=true";
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
