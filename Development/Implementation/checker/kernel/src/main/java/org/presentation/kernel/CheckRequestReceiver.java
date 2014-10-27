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
 * <p>
 * CheckRequestReceiver class.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@LocalBean
@Stateless
public class CheckRequestReceiver {

    @EJB
    private PersistenceFacade persistenceFacade;

    @EJB
    private CheckingExecutionQueue execQueue;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    /**
     * <p>
     * addNewCheckingRequest.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @param checkingRequest a {@link org.presentation.kernel.CheckingRequest}
     * object.
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
     * <p>
     * stopSpecificChecking.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
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
