package org.presentation.kernel.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.presentation.persistence.business.PersistenceFacade;
import org.presentation.persistence.model.CheckState;
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

    private final LinkedBlockingQueue<Checkup> queue;

    private final ConcurrentHashMap<Integer, CheckingExecutor> runningCheckings;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    
    @Resource
    private SessionContext context;

    public CheckingExecutionQueue() {
        queue = new LinkedBlockingQueue<>();
        runningCheckings = new ConcurrentHashMap<>();
    }

    @PostConstruct
    protected void init() {
        //initialize singleton bean at startup
        //thanks to Startup annotation this is called during deployment
        LOG.info("Initializing CheckingExecutionQueue EJB Singleton bean");
        //repopulating queue after startup
        LOG.info("Started repopulating execution queue");
        List<Checkup> checkups = persistentFacade.findNotEndedCheckupsStateOrdered();
        for (Checkup i : checkups){
            if (i.getState() != CheckState.PENDING){
                i.setState(CheckState.PENDING);
                persistentFacade.updateCheckup(i);
            }
            queue.add(persistentFacade.findCheckupInitializedInputs(i.getIdCheckup()));
        }
        LOG.log(Level.INFO, "{0} elements added to execution queue", queue.size());
        //create execution threads
        LOG.log(Level.INFO, "Preparing to create {0} execution threads", MAX_WORKING_THREADS);
        for (int i = 0; i < MAX_WORKING_THREADS; i++){
            context.getBusinessObject(CheckingExecutionQueue.class).newThread();
        }
        LOG.log(Level.INFO, "Execution threads created");
    }

    private final Lock lock = (Lock) new ReentrantLock();
    
    @Asynchronous
    public void notifyNewRequests() {
        lock.lock();
        try {
            Checkup checkup = persistentFacade.fetchNewlyCreatedCheckup();
            queue.add(checkup);
            LOG.log(Level.INFO, "New checkup with id {0} added into execution queue", checkup.getIdCheckup());
        } finally {
            lock.unlock();
        }
        
    }

    @Asynchronous
    public void newThread() {
        LOG.info("Creating new CheckingExecutionQueue thread");
        //TODO implement
//        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void stopRunningChecking(Integer checkupId) {
        LOG.info("Called method to stop running checking");
        //TODO implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
