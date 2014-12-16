/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.presentation.persistence.model.Login;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * This bean provides the login history listing ability
 *
 * @author petrof
 * @version $Id: $Id
 */
@Named
@RequestScoped
public class LoginHistoryBean extends ProtectedBean {

    protected LazyDataModel<Login> lazyLoginHistory;

    /**
     * This getter creates a lazydata model for primefaces pagination
     *
     * @return lazy data model
     */
    public LazyDataModel<Login> getLazyLoginHistory() throws UserAuthorizationException {

        if (lazyLoginHistory != null) {
            return lazyLoginHistory;
        }

        final User loggedUser = getLoggedUser();

        this.lazyLoginHistory = new LazyDataModel<Login>() {

            @Override
            public List<Login> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<Login> allLogins;
                List<Login> logins;

                // todo change persistance layer to do this more efficiently
                allLogins = persistance.findUserLogins(loggedUser);

                lazyLoginHistory.setRowCount(allLogins.size());

                if (allLogins.size() > first) {
                    if (allLogins.size() > first + pageSize) {
                        logins = allLogins.subList(first, first + pageSize);
                    } else {
                        logins = allLogins.subList(first, allLogins.size() - 1);
                    }
                } else {
                    logins = new ArrayList<>();
                }

                return logins;
            }

        };

        return this.lazyLoginHistory;
    }

    /**
     * <p>
     * Setter for the field <code>lazyLoginHistory</code>.</p>
     *
     * @param lazyLoginHistory a {@link org.primefaces.model.LazyDataModel}
     * object.
     */
    public void setLazyLoginHistory(LazyDataModel<Login> lazyLoginHistory) {
        this.lazyLoginHistory = lazyLoginHistory;
    }

}
