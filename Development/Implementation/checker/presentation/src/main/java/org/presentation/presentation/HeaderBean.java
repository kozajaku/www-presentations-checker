package org.presentation.presentation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;

/**
 * This bean is designed to fulfill data requests from header section of the
 * page
 *
 * @author petrof
 * @version $Id: $Id
 */
@Named
@RequestScoped
public class HeaderBean extends ProtectedBean {

    /**
     * Decides if the user is logged in
     *
     * @return user is logged in?
     */
    public boolean isLoggedIn() {
        try {
            return this.getLoggedUser() == null ? false : true;
        } catch (UserAuthorizationException ex) {
            return false;
        }
    }

    /**
     * Get user that is currently logged in
     *
     * @return currently logged-in user
     */
    public User getUser() throws UserAuthorizationException {
        try {
            return this.getLoggedUser();
        } catch (UserAuthorizationException ex) {
            return null;
        }
    }

}
