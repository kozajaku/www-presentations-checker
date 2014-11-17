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
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.presentation.presentation.helper.CheckupEnvelope;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * This bean provides the checkup listing ability
 *
 * @author petrof
 * @version $Id: $Id
 */
@Named
@RequestScoped
public class CheckupListBean extends ProtectedBean {

    protected LazyDataModel<CheckupEnvelope> lazyCheckupList;

    // old un-paginated crap
    /*
     public List<CheckupEnvelope> getCheckupList() throws UserAuthorizationException {		
     List<Checkup> checkups;	
     List<CheckupEnvelope> checkupList = new ArrayList<>();
		
     checkups = persistance.findUserCheckings(this.getLoggedUser());

     for(Checkup checkup: checkups) {
     checkup.setOptionList(persistance.findCheckupOptions(checkup));

     checkupList.add(new CheckupEnvelope(
     checkup,
     persistance.findCheckupDomains(checkup),
     checkup.getOptionList()
     ));
     }
	    
     return checkupList;
	
     }    
     */
    /**
     * This getter creates a lazydata model for primefaces pagination
     *
     * @return lazy data model
     * @throws
     * org.presentation.presentation.exception.UserAuthorizationException if
     * any.
     */
    public LazyDataModel<CheckupEnvelope> getLazyCheckupList() throws UserAuthorizationException {
        if (lazyCheckupList != null) {
            return lazyCheckupList;
        }

        final User loggedUser = getLoggedUser();

        this.lazyCheckupList = new LazyDataModel<CheckupEnvelope>() {

            @Override
            public List<CheckupEnvelope> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<Checkup> checkups;
                List<CheckupEnvelope> checkupList = new ArrayList<>();

                lazyCheckupList.setRowCount(persistance.findUserCheckings(loggedUser).size());	// todo replace by .countUserCheckings(user)

                checkups = persistance.findUserCheckings(loggedUser, first, pageSize);

                for (Checkup checkup : checkups) {
                    checkup.setOptionList(persistance.findCheckupOptions(checkup));

                    checkupList.add(new CheckupEnvelope(
                            checkup,
                            persistance.findCheckupDomains(checkup),
                            checkup.getOptionList()
                    ));
                }

                return checkupList;

            }

        };

        return this.lazyCheckupList;
    }

    /**
     * <p>
     * Setter for the field <code>lazyCheckupList</code>.</p>
     *
     * @param lazyCheckupList a {@link org.primefaces.model.LazyDataModel}
     * object.
     */
    public void setLazyCheckupList(LazyDataModel<CheckupEnvelope> lazyCheckupList) {
        this.lazyCheckupList = lazyCheckupList;
    }

}
