package org.presentation.kernel.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.presentation.persistence.business.PersistenceFacade;
import org.presentation.persistence.model.Checkup;

/**
 *
 * @author radio.koza
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class CheckingExecutionQueue {
    ///set constant for max threads count
    private static final int MAX_WORKING_THREADS = 5;
    
    @Inject
    private Instance<CheckingExecutor> checkingExecutorPrototype;
    
    @EJB
    private PersistenceFacade persistentFacade;
    
    private LinkedBlockingQueue<Checkup> queue;
    
    private ConcurrentHashMap<Integer, CheckingExecutor> runningCheckings;
    
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    
    @PostConstruct
    protected void init(){
        //initialize singleton bean at startup
        //thanks to Startup annotation this is called during deployment
        LOG.info("Initializing CheckingExecutionQueue EJB Singleton bean");
    }
    
    public void notifyNewRequests(){
        LOG.info("Notify called - new checkup request in database");
        //TODO implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Asynchronous
    public void newThread(){
        LOG.info("Creating new CheckingExecutionQueue thread");
        //TODO implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public void stopRunningChecking(Integer checkupId){
        LOG.info("Called method to stop running checking");
        //TODO implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
