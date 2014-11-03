/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.Map;
import javax.faces.context.FacesContext;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;

/**
 * This bean is a super class for beans that are available to logged users only
 *
 * @author petrof
 * @version $Id: $Id
 */
public class ProtectedBean extends CommonBean {

    /**
     * This method returns the instance of currently logged user
     *
     * @return user that is logged in in current session
     * @throws org.presentation.presentation.exception.UserAuthorizationException
     */
    protected User getLoggedUser() throws UserAuthorizationException {

        // real code
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if (sessionMap == null) {
            throw new UserAuthorizationException("Session data corruption");
        }
        User user = null;
        try {
            user = (User) sessionMap.get("user");
        } catch (ClassCastException ex) {
            throw new UserAuthorizationException("Session data corruption");
        }
        if (user == null) {
            throw new UserAuthorizationException("Session data corruption");
        }
        return user;

        // testing-purposes substitution
//	User user;
//	user = persistance.findUser("test@test.cz");
//	if(user == null) throw new UserAuthorizationException("User test@test.cz not found in the database");
//	return user; 
    }

}
