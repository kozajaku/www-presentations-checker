/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.text.MessageFormat;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.presentation.presentation.validation.ValidEmail;
import org.presentation.presentation.validation.ValidPassword;

/**
 * This bean provides account creation ability
 *
 * @author petrof
 * @version $Id: $Id
 */
@Named
@RequestScoped
public class SignupBean extends CommonBean {

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    @ValidPassword
    private String passwordVerification;

    @Size(min = 2)
    @NotNull
    private String name;

    @Size(min = 2)
    @NotNull
    private String surname;

    /**
     * this action is called to create new account
     *
     * @return jsf view
     * @throws java.lang.Exception if any.
     */
    public String signUp() throws Exception {

        if (persistance.findUser(email) != null) {
            this.addMessage(new FacesMessage(MessageFormat.format(this.msg.getString("signUp.account_already_exists"), email)));
            return "";
        }

        if (!this.password.equals(this.passwordVerification)) {
            this.addMessage(new FacesMessage(this.msg.getString("signUp.password_verification_mismatch")));
            return "";
        }

        if (persistance.createNewUser(email, password, name, surname) == true) {
            return "/protected/user/index.xhtml?faces-redirect=true";
        } else {
            this.addMessage(new FacesMessage(this.msg.getString("signUp.fail_message")));
        }

        return "";
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

    /**
     * <p>
     * Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Getter for the field <code>surname</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * <p>
     * Setter for the field <code>surname</code>.</p>
     *
     * @param surname a {@link java.lang.String} object.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * <p>
     * Getter for the field <code>passwordVerification</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPasswordVerification() {
        return passwordVerification;
    }

    /**
     * <p>
     * Setter for the field <code>passwordVerification</code>.</p>
     *
     * @param passwordVerification a {@link java.lang.String} object.
     */
    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

}
