package org.presentation.kernel.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
    private Logger LOG;
    
    @PostConstruct
    protected void init(){
        //initialize singleton bean at startup
        //thanks to Startup annotation this is called during deployment
    }
    
    
}
