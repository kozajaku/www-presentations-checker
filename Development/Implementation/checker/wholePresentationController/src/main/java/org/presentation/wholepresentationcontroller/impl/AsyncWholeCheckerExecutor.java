package org.presentation.wholepresentationcontroller.impl;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.parser.AbstractCode;
import org.presentation.wholepresentationcontroller.WholePresentationChecker;

/**
 * Class representing task that will be executed asynchronously in new thread.
 * This class is used as asynchronous queue for delegation addPage and finalize
 * to submodule implementations.
 *
 * @author radio.koza
 */
public class AsyncWholeCheckerExecutor implements Runnable {

    private final List<WholePresentationChecker> checkers;
    private final LinkedBlockingQueue<WholeExecQueueElement> queue;

    private static final Logger LOG = Logger.getLogger(AsyncWholeCheckerExecutor.class.getName());

    /**
     * Constructor initialized by allowed checker module implementations.
     *
     * @param checkers Allowed checkers
     */
    public AsyncWholeCheckerExecutor(List<WholePresentationChecker> checkers) {
        this.checkers = checkers;
        queue = new LinkedBlockingQueue<>();
    }

    /**
     * Add new request to delegate page to checkers.
     *
     * @param pageContent Content of the page
     * @param link Source link of the page
     * @param contentType Type of the page itself
     */
    public void enqueueNewPage(PageContent pageContent, LinkURL link, ContentType contentType) {
        WholeExecQueueElement page = new WholeExecQueueElement(pageContent, link, contentType);
        queue.add(page);
    }

    /**
     * Delegate finalization to the implemented allowed submodules.
     *
     * @param traversalGraph {@link TraversalGraph} that can be used as source
     * for more information
     */
    public void finalizeCheckup(TraversalGraph traversalGraph) {
        WholeExecQueueElement finalElement = new WholeExecQueueElement(traversalGraph);
        queue.add(finalElement);
    }

    /**
     * Method is called in the new thread. It is implemented on blocking queue
     * that receives new requests for asynchronous processing.
     */
    @Override
    public void run() {
        try {
            WholeExecQueueElement queueElement;
            while (true) {
                queueElement = queue.take();
                //check last element
                if (queueElement.isEndOfQueue()) {
                    LOG.info("Retrieving final element from wpc queue");
                    break;
                }
                LOG.info("Retrieving new page from wpc queue");
                //page element is valid
                AbstractCode abstractCode = AbstractCode.createCode(queueElement.getContentType(), queueElement.getLink(), queueElement.getPageContent());
                for (WholePresentationChecker i : checkers) {
                    i.addPage(abstractCode);
                }
            }
            //continue to whole presentation check
            for (WholePresentationChecker i : checkers) {
                i.finalizeCheckup(queueElement.getTraversalGraph());
            }
        } catch (InterruptedException ex) {
            //interruption means server is shutting down
            //nothing necessary to do
        }
    }

}
