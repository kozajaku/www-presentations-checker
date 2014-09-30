package org.presentation.kernel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.presentation.kernel.impl.CheckingExecutionQueue;
import org.presentation.persistence.business.PersistenceFacade;

/**
 *
 * @author radio.koza
 */
@LocalBean
@Stateless
public class CheckRequestReceiver {

    @EJB
    private PersistenceFacade persistenceFacade;

    @EJB
    private CheckingExecutionQueue execQueue;

    @Inject
    private Logger LOG;

    void addNewCheckingRequest(CheckingRequest checkingRequest) {
        //TODO not implemented
        LOG.log(Level.INFO, "addNewCheckingRequest called");
        throw new UnsupportedOperationException("Not implemented yet");
    }

    void stopSpecificChecking(Integer checkupId) {
        //TODO not implemented
        LOG.log(Level.INFO, "stopSpecificChecking called");
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
