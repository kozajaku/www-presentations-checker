/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.Map;
import javax.faces.context.FacesContext;
import org.presentation.persistence.model.User;

/**
 *
 * @author petrof
 */
public class ProtectedBean extends CommonBean {
    
    protected User getLoggedUser() throws Exception {
	Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	if(sessionMap == null) throw new Exception("Session data corruption");
	Object o = sessionMap.get("userId");
	if(o == null) throw new Exception("Session data corruption");
	User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
	if(user == null) throw new Exception("Session data corruption");
	return user;
    }
    
}
