package org.presentation.presentation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.presentation.presentation.helper.PageLink;

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
            return this.getLoggedUser() != null;
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

    /**
     * <p>
     * getMainMenu.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<PageLink> getMainMenu() {
        List<PageLink> pages = new ArrayList<>();

        if (this.isLoggedIn()) {
            pages.add(new PageLink("/protected/user/checkupList.xhtml", getMenuItemTranslation("checkup_list")));
            pages.add(new PageLink("/protected/user/newCheckup.xhtml", getMenuItemTranslation("new_checkup")));
            pages.add(new PageLink("/protected/user/loginHistory.xhtml", getMenuItemTranslation("login_history")));
            pages.add(new PageLink("/protected/user/userSettings.xhtml", getMenuItemTranslation("user_settings")));
        } else {
            pages.add(new PageLink("/public/login.xhtml", getMenuItemTranslation("log_in")));
            pages.add(new PageLink("/public/signUp.xhtml", getMenuItemTranslation("sign_up")));
        }

        return pages;
    }

    /**
     * <p>
     * getMenuItemTranslation.</p>
     *
     * @param menuItemId a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    protected String getMenuItemTranslation(String menuItemId) {
        try {
            String s = msg.getString("navi." + menuItemId);
            if (s != null) {
                return s;
            }
        } catch (MissingResourceException e) {
        }
        return menuItemId;
    }

}
