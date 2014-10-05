package org.presentation.kernel.impl;

import java.util.Date;
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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
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
    private PersistenceFacade persistenceFacade;

    private final LinkedBlockingQueue<Checkup> queue;

    private final ConcurrentHashMap<Integer, CheckingExecutor> runningCheckings;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    @Resource
    private ManagedExecutorService mes;
    
    public CheckingExecutionQueue() {
        queue = new LinkedBlockingQueue<>();
        runningCheckings = new ConcurrentHashMap<>();
    }

    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    protected void init() {
        //initialize singleton bean at startup
        //thanks to Startup annotation this is called during deployment
        LOG.info("Initializing CheckingExecutionQueue EJB Singleton bean");
        //repopulating queue after startup
        LOG.info("Started repopulating execution queue");
        List<Checkup> checkups = persistenceFacade.findNotEndedCheckupsStateOrdered();
        for (Checkup i : checkups) {
            if (i.getState() != CheckState.PENDING) {
                i.setState(CheckState.PENDING);
                persistenceFacade.updateCheckup(i);
            }
            queue.add(i);
        }
        LOG.log(Level.INFO, "{0} elements added to execution queue", queue.size());
        //create execution threads
        LOG.log(Level.INFO, "Preparing to create {0} execution threads", MAX_WORKING_THREADS);
        for (int i = 0; i < MAX_WORKING_THREADS; i++) {
            mes.submit(new Runnable() {

                @Override
                public void run() {
                    newThread();
                }
            });
        }
        LOG.log(Level.INFO, "Execution threads created");
    }

    private final Lock newRequestLock = new ReentrantLock();

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void notifyNewRequests() {
        newRequestLock.lock();
        try {
            Checkup checkup = persistenceFacade.fetchNewlyCreatedCheckup();
            LOG.log(Level.INFO, "New checkup with id {0} added into execution queue", checkup.getIdCheckup());
            queue.add(checkup);
        } finally {
            newRequestLock.unlock();
        }

    }

    private final Lock stoppingLock = new ReentrantLock();

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void newThread() {
        LOG.info("Creating new CheckingExecutionQueue thread");
        CheckingExecutor executor;
        while (true) {
            try {
                //infinite loop for this thread
                Checkup checkup = queue.take();
                LOG.log(Level.INFO, "Removing checkup with id {0} from execution queue", checkup.getIdCheckup());
                //check if checkup has still pending state and is not stopped
                stoppingLock.lock();
                try {
                    checkup = persistenceFacade.findCheckup(checkup.getIdCheckup());
                    if (!checkup.getState().equals(CheckState.PENDING)) {
                        LOG.log(Level.INFO, "Checkup is not in state pending - ignoring");
                        continue;
                    }
                    //set state of checkup
                    checkup.setState(CheckState.CHECKING);
                    persistenceFacade.updateCheckup(checkup);
                    //dynamically inject new CheckingExecutor instance
                    executor = checkingExecutorPrototype.get();
                    runningCheckings.put(checkup.getIdCheckup(), executor);
                } finally {
                    stoppingLock.unlock();
                }
                executor.startChecking(checkup);//this method block until end of checking
                try {
                    stoppingLock.lock();
                    checkup = persistenceFacade.findCheckup(checkup.getIdCheckup());
                    if (checkup.getState().equals(CheckState.CHECKING)){
                        checkup.setState(CheckState.FINISHED);
                        checkup.setCheckingFinished(new Date());
                        persistenceFacade.updateCheckup(checkup);
                    }
                    runningCheckings.remove(checkup.getIdCheckup());
                } finally {
                    stoppingLock.unlock();
                }
                LOG.log(Level.INFO, "Checking of checkup with id {0} finished", checkup.getIdCheckup());
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, "Execution queue thread interrupted", ex);
            }
        }
    }

    public void stopRunningChecking(Integer checkupId) {
        LOG.info("Called method to stop running checking");
        //TODO implement
        //TODO must use stoppingLock
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
