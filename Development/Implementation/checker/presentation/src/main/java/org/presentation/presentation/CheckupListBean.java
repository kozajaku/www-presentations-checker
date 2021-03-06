/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.presentation.kernel.CheckRequestReceiver;
import org.presentation.kernel.Progress;
import org.presentation.persistence.model.CheckState;
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

    @EJB
    protected CheckRequestReceiver checkRequestReceiver;

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

                lazyCheckupList.setRowCount(persistance.countUserCheckups(loggedUser));

                checkups = persistance.findUserCheckings(loggedUser, first, pageSize);

                for (Checkup checkup : checkups) {

                    Progress progress = null;
                    int percentDone = -1;
                    if (checkRequestReceiver != null && checkup.getState() == CheckState.CHECKING) {
                        try {
                            progress = checkRequestReceiver.getPagesCrawled(checkup.getIdCheckup());
                            if (progress != null) {
                                percentDone = (int) progress.percentDone();
                            }
                        } catch (NoSuchFieldException ex) {
                            LOG.log(Level.WARNING, ex.toString(), ex);
                        }
                    }

                    checkup.setOptionList(persistance.findCheckupOptions(checkup));

                    checkupList.add(new CheckupEnvelope(
                            checkup,
                            persistance.findCheckupDomains(checkup),
                            checkup.getOptionList(),
                            progress
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
