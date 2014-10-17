package org.presentation.singlepagecontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MessageProducer;
import org.presentation.singlepagecontroller.impl.AsyncSPCCaller;
import org.presentation.utils.OptionContainer;
import org.presentation.utils.Stoppable;

/**
 *
 * @author radio.koza
 */
@Dependent
public class SinglePageController implements MessageProducer, Stoppable {

    ///Instance object for requesting SinglePageControllerService prototypes
    @Inject
    private Instance<SinglePageControllerService> singlePageCheckersPrototype;

    ///Real SinglePageControllerService implemented instances created at time of spc initializeControllers calling
    private List<SinglePageControllerService> singlePageCheckers;

    ///ExecutorService for pooling free worker threads
    @Resource
    private ManagedExecutorService mes;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    
    /**
     * Method initialize controller services for this checkup by injecting all
     * possible implementations and than choosing those, which was chosed by
     * user (choice passed in parameter options). This method <b>MUST</b> be
     * called before {@link #checkPage(org.presentation.model.ContentType, org.presentation.model.LinkURL, org.presentation.model.PageContent)
     * }
     * or the IllegalStateException will be thrown.
     *
     * @param options Options allowed for spc checking
     */
    public void initializeControllers(OptionContainer options) {
        singlePageCheckers = new ArrayList<>();
        for (SinglePageControllerService i : singlePageCheckersPrototype) {
            if (options.getChosenOptions().contains(i.getID())) {//in future may be implemented effectively by using Set
                singlePageCheckers.add(i);
            }
        }
    }

    /**
     * Method is called by web crawler (delegated from kernel) for every valid
     * page validation can be applied on. Method must delegate these requests
     * paralelly to chosen implementations of
     * {@link SinglePageControllerService} class. Method {@link #initializeControllers(org.presentation.utils.OptionContainer)
     * must be called before this method or IllegalStateException will be thrown.
     *
     * @param contentType Type of page content (eg. text/html, etc...)
     * @param linkURL URL of the checked page
     * @param pageContent Source code of checking page
     *
     * @throws IllegalStateException Thrown if {@link #initializeControllers(org.presentation.utils.OptionContainer)
     * was not called before calling this method.
     */
    public void checkPage(ContentType contentType, LinkURL linkURL, PageContent pageContent) {
        //check that initializeControllers method was called
        if (singlePageCheckers == null) {
            //was not called
            throw new IllegalStateException("Method initializeControllers was not called before checkPage!");
        }
        List<Future<?>> futures = new ArrayList<>();
        AsyncSPCCaller asyncTask;
        for (SinglePageControllerService i: singlePageCheckers){
            if (i.isApplicable(contentType)){
                asyncTask = new AsyncSPCCaller(contentType, linkURL, pageContent, i);
                futures.add(mes.submit(asyncTask));
            }
        }
        //all tasks submitted - wait for end
        for (Future<?> i: futures){
            try {
                i.get();
            } catch (InterruptedException | ExecutionException ex) {
                LOG.log(Level.SEVERE, "Exception during waiting for end of spc tasks", ex);
            }
        }
    }

    /**
     * Setting messageLogger to chosen controllers. Note that {@link #initializeControllers(org.presentation.utils.OptionContainer)
     * must be called before this or IllegalStateException will be thrown.
     *
     * @param messageLoggerContainer Logger container for creating new logger
     * @throws IllegalStateException If {@link #initializeControllers(org.presentation.utils.OptionContainer)} was not called first.
     */
    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer) {
        if (singlePageCheckers == null){
            throw new IllegalStateException("Method initializeControllers was not called before checkPage!");
        }
        for (SinglePageControllerService i: singlePageCheckers){
            i.offerMsgLoggerContainer(messageLoggerContainer);
        }
    }

    @Override
    public void stopChecking() {
        //delegating to spc service implementations
        if (singlePageCheckers != null){
            for (SinglePageControllerService i: singlePageCheckers){
                i.stopChecking();
            }
        }
    }

}
