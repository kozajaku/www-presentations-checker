/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.presentation.presentation.validation.ValidPassword;

/**
 * <p>
 * UserSettingsBean class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
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

    @NotNull
    @ValidPassword
    private String currentPassword;

    @Size(min = 2)
    @NotNull
    private String name = "";

    @Size(min = 2)
    @NotNull
    private String surname = "";

    private User user = null;

    /**
     * <p>
     * loadUser.</p>
     *
     * @throws
     * org.presentation.presentation.exception.UserAuthorizationException if
     * any.
     */
    protected void loadUser() throws UserAuthorizationException {
        if (user == null) {
            user = this.getLoggedUser();
        }
    }

    /**
     * <p>
     * Getter for the field <code>password</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPassword() {
        return "";
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

    /**
     * <p>
     * Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     * @throws
     * org.presentation.presentation.exception.UserAuthorizationException if
     * any.
     */
    public String getName() throws UserAuthorizationException {
        loadUser();
        return (this.user == null ? "" : this.user.getName());
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
     * @throws
     * org.presentation.presentation.exception.UserAuthorizationException if
     * any.
     */
    public String getSurname() throws UserAuthorizationException {
        loadUser();
        return (this.user == null ? "" : this.user.getSurname());
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
     * Getter for the field <code>currentPassword</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * <p>
     * Setter for the field <code>currentPassword</code>.</p>
     *
     * @param currentPassword a {@link java.lang.String} object.
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * <p>
     * updateSettings.</p>
     *
     * @return a {@link java.lang.String} object.
     * @throws
     * org.presentation.presentation.exception.UserAuthorizationException if
     * any.
     */
    public String updateSettings() throws UserAuthorizationException {
        loadUser();
        user.setName(name);
        user.setSurname(surname);
        this.persistance.editUser(user);
        return "userSettingsResult?type=settingsChange&faces-redirect=true";
    }

    /**
     * <p>
     * changePassword.</p>
     *
     * @return a {@link java.lang.String} object.
     * @throws
     * org.presentation.presentation.exception.UserAuthorizationException if
     * any.
     */
    public String changePassword() throws UserAuthorizationException {
        loadUser();
        if (!this.password.equals(this.passwordVerification)) {
            this.addMessage(new FacesMessage(this.msg.getString("userSettings.password_verification_mismatch")));
            return "";
        }
        persistance.changeUserPassword(user, currentPassword, password);
        return "userSettingsResult?type=passwordChange&faces-redirect=true";
    }

}
