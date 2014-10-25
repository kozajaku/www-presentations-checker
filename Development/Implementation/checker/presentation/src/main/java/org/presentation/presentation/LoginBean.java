/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

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
 * this bean handles user login and logout operations
 *
 * @author petrof
 * @version $Id: $Id
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

    /**
     * this action performs user login operation
     *
     * @return jsf view
     * @throws javax.servlet.ServletException if any.
     * @throws javax.servlet.ServletException if any.
     * @throws java.lang.Exception if any.
     */
    public String login() throws ServletException, Exception {

        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        User user = persistance.findUser(email);
        if (user == null) {
            this.addMessage(new FacesMessage(msg.getString("login.user_not_found")));
            return "";
        }

        try {
            request.login(email, password);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
            this.persistance.addUserLogin(user, ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
        } catch (ServletException e) {
            this.addMessage(new FacesMessage(msg.getString("login.login_fail_message")));
            return "";
        }

        return "/protected/user/index?faces-redirect=true";
    }

    /**
     * this action performs the logout operation
     *
     * @return jsf view
     */
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

    /**
     * <p>
     * Getter for the field <code>email</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>
     * Setter for the field <code>email</code>.</p>
     *
     * @param email a {@link java.lang.String} object.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>
     * Getter for the field <code>password</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * Setter for the field <code>password</code>.</p>
     *
     * @param password a {@link java.lang.String} object.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
