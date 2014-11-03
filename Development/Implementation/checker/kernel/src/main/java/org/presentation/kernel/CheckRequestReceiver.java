package org.presentation.kernel;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.presentation.kernel.impl.CheckingExecutionQueue;
import org.presentation.persistence.business.PersistenceFacade;
import org.presentation.persistence.model.CheckState;
import org.presentation.persistence.model.Checkup;

/**
 * EJB Stateless bean which serves as business facade for creating new checking
 * request, stop currently running request and fetching more information about
 * running request. This ejb bean although cannot be used to fetch complete
 * information about finished request. The persistence module should be used for
 * this purpose.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@LocalBean
@Stateless
public class CheckRequestReceiver {

    ///Proxy object to implementation of PersistenceFacade ejb bean
    @EJB
    private PersistenceFacade persistenceFacade;

    ///Proxy object to ejb singleton checking execution queue
    @EJB
    private CheckingExecutionQueue execQueue;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    /**
     * This method is used for creation of new checking request. Method simply
     * persists information through persistence facade to database and inform
     * checking execution queue, that new checking request is available. New
     * checking must be always assigned to specifiec user. Note that this method
     * is not secured through JAAS so this security functionality must be
     * implemented in tier calling this method.
     *
     * @param email Email of the user requesting new checking request; email is
     * represented by {@link java.lang.String} instance
     * @param checkingRequest Instance of
     * {@link org.presentation.kernel.CheckingRequest} holding information about
     * new checking request
     */
    public void addNewCheckingRequest(String email, CheckingRequest checkingRequest) {
        LOG.log(Level.INFO, "addNewCheckingRequest called for user {0}", email);
        Checkup checkup = new Checkup();
        checkup.setCheckingCreated(new Date());//set this date
        checkup.setCheckingInterval(checkingRequest.getRequestInterval());
        checkup.setMaxDepth(checkingRequest.getMaxDepth());
        checkup.setStartPoint(checkingRequest.getStartingPoint().getUrl());
        checkup.setPageLimit(checkingRequest.getPageLimit());
        checkup.setState(CheckState.CREATED);
        checkup.setUser(persistenceFacade.findUser(email));
        persistenceFacade.createNewCheckup(checkup);
        persistenceFacade.flush();
        persistenceFacade.addDomainsToCheckup(checkup, checkingRequest.getAllowedDomains());
        persistenceFacade.addHeadersToCheckup(checkup, checkingRequest.getHeaders());
        persistenceFacade.addOptionsToCheckup(checkup, checkingRequest.getChosenOptions().getChosenOptions());
        LOG.log(Level.INFO, "created new Checkup in database with primary key {0}", checkup.getIdCheckup());
        execQueue.notifyNewRequests();
    }

    /**
     * Method stops request represented by primary key. Primary key is the same
     * as Checkup object persisted in database. If checking of this request did
     * not start already Checkup is simply set as stoppped before start. If
     * checking is in progress stop is delegated to execution queue.
     *
     * @param checkupId Primary key of the Checkup to be stopped
     */
    public void stopSpecificChecking(Integer checkupId) {
        LOG.log(Level.INFO, "stopSpecificChecking called");
        Checkup checkup = persistenceFacade.findCheckup(checkupId);
        if (checkup == null) {
            LOG.log(Level.WARNING, "Checkup to stop with specified primary key {0} was not found", checkupId);
            return;
        }
        if (checkup.getState().isEnded()) {
            LOG.log(Level.INFO, "Unable to stop ended checking");
            return;
        }
        if (checkup.getState().equals(CheckState.CREATED) || checkup.getState().equals(CheckState.PENDING)) {
            checkup.setState(CheckState.STOPPED_BEFORE_START);
            persistenceFacade.updateCheckup(checkup);
        } else {
            execQueue.stopRunningChecking(checkupId);
        }
    }
}
