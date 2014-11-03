package org.presentation.wholepresentationcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MessageProducer;
import org.presentation.utils.OptionContainer;
import org.presentation.utils.Stoppable;
import org.presentation.wholepresentationcontroller.impl.AsyncWholeCheckerExecutor;

/**
 * Main class of this module that serves as delegator to the implemented
 * submodules.
 *
 * @author radio.koza
 */
@Dependent
public class WholePresentationController implements Stoppable, MessageProducer {

    ///Prototype of injectable implementations of {@link WholePresentationChecker} interface
    @Inject
    @Any
    private Instance<WholePresentationChecker> wholePresentationCheckersPrototype;
    ///Real implementation of {@link WholePresentationChecker} interface used in this run
    private List<WholePresentationChecker> wholePresentationCheckers;
    ///Executor used for requesting new threads from server pool
    @Resource
    private ManagedExecutorService mes;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    private AsyncWholeCheckerExecutor asyncExec;
    private Future<?> asyncExecFuture;
    private final Lock instantiationLock = new ReentrantLock();
    private final Lock startedThreadLock = new ReentrantLock();

    /**
     * Method initialize controller services for this checkup by injecting all
     * possible implementations and than choosing those, which was chosed by
     * user (choice passed in parameter options). This method <b>MUST</b> be
     * called before adding page sources to the controller or calling method
     * {@link #offerMsgLoggerContainer(org.presentation.model.logging.MessageLoggerContainer)}
     * or the IllegalStateException will be thrown.
     *
     * @param options Options allowed for spc checking
     */
    public void initializeControllers(OptionContainer options) {
        wholePresentationCheckers = new ArrayList<>();
        for (WholePresentationChecker i : wholePresentationCheckersPrototype) {
            if (options.getChosenOptions().contains(i.getID())) {//in future may be implemented effectively by using Set
                wholePresentationCheckers.add(i);
            }
        }
        if (!wholePresentationCheckers.isEmpty()) {
            startedThreadLock.tryLock();
        }
    }

    /**
     * {@inheritDoc}
     *
     * Method sets messageLogger to chosen controllers. Note that
     * {@link #initializeControllers(org.presentation.utils.OptionContainer)}
     * must be called before this or IllegalStateException will be thrown.
     */
    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer) {
        if (wholePresentationCheckers == null) {
            throw new IllegalStateException("Call initializeControllers method first!");
        }
        for (WholePresentationChecker i : wholePresentationCheckers) {
            i.offerMsgLoggerContainer(messageLoggerContainer);
        }
    }

    @Override
    public void stopChecking() {
        if (wholePresentationCheckers == null) {
            return;
        }
        for (WholePresentationChecker i : wholePresentationCheckers) {
            i.stopChecking();
        }
    }

    /**
     * Method thread-safely instantiates new asyncExecutionQueue, if it is not
     * instantiated already.
     */
    private void instantiateAsyncExecutionQueue() {
        instantiationLock.lock();
        try {
            //double checking
            if (asyncExec == null) {
                asyncExec = new AsyncWholeCheckerExecutor(wholePresentationCheckers);
                //start new thread
                asyncExecFuture = mes.submit(asyncExec);
                startedThreadLock.unlock();
            }
        } finally {
            instantiationLock.unlock();
        }
    }

    /**
     * Method is called for every new page, the web crawler browse. Pages are
     * asynchronously delegated to implementing submodules. Note that
     * {@link #initializeControllers(org.presentation.utils.OptionContainer)}
     * must be called first or {@link IllegalStateException} will be thrown.
     *
     * @param pageContent Content of the page itself
     * @param linkURL Source of the page
     * @param contentType Type of the page
     */
    public void addPage(PageContent pageContent, LinkURL linkURL, ContentType contentType) {
        if (wholePresentationCheckers == null) {
            throw new IllegalStateException("Call initializedControllers method first!");
        }
        if (wholePresentationCheckers.isEmpty()) {
            return;//no controllers in option - nothing to do
        }
        if (asyncExec == null) {
            instantiateAsyncExecutionQueue();//thread safe method
        }
        asyncExec.enqueueNewPage(pageContent, linkURL, contentType);
    }

    /**
     * Final method called when the crawling is done.
     *
     * @param traversalGraph {@link TraversalGraph} object that can be used for
     * more information for implemented submodules
     */
    public void checkPresentation(TraversalGraph traversalGraph) {
        LOG.info("Calling checkPresentation on WholePresentationController");
        if (wholePresentationCheckers == null) {
            throw new IllegalStateException("Call initializedControllers method first!");
        }
        if (wholePresentationCheckers.isEmpty()) {
            return;//no controllers in option - nothing to do
        }
        //for sure - if no page was added before calling this method
        if (asyncExec == null) {
            instantiateAsyncExecutionQueue();//thread safe method
        }
        asyncExec.finalizeCheckup(traversalGraph);
    }

    /**
     * Method returns {@link Future} object connected to the asynchronous
     * execution queue. Owner of this instance can then block and wait to the
     * end of the queue thread.
     *
     * @return {@link Future} connected to asynchronous queue thread
     */
    public Future<?> getExecutionFuture() {
        startedThreadLock.lock();//wait till start of the thread
        try {
            if (asyncExecFuture != null) {//means checkers are in option
                return asyncExecFuture;
            }
            //no checkers
            //return completed Future implementation
            return new Future<Object>() {

                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    return false;
                }

                @Override
                public boolean isCancelled() {
                    return false;
                }

                @Override
                public boolean isDone() {
                    return true;
                }

                @Override
                public Object get() throws InterruptedException, ExecutionException {
                    return new Object();
                }

                @Override
                public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                    return new Object();
                }
            };
        } finally {
            startedThreadLock.unlock();//not necessary but to be sure
        }
    }

}
