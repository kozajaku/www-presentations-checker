package org.presentation.testcreatenewuser;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.presentation.checker.persistence.business.PersistenceFacade;

/**
 *
 * @author radio.koza
 */
@Named
@RequestScoped
public class CreateUserBean {

    @Inject
    private PersistenceFacade facade;

    private String email;
    private String password;
    private String name;
    private String surname;

    public String getEmail() {
        return null;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return null;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String createNewUser() {
        facade.createNewUser(email, password, name, surname);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true"; //no navigation
    }

}
