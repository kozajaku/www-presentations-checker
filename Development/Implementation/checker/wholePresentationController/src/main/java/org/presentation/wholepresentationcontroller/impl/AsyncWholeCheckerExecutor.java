package org.presentation.wholepresentationcontroller.impl;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.parser.AbstractCode;
import org.presentation.wholepresentationcontroller.WholePresentationChecker;

/**
 * @author radio.koza
 */
public class AsyncWholeCheckerExecutor implements Runnable {

    private final List<WholePresentationChecker> checkers;
    private final LinkedBlockingQueue<WholeExecQueueElement> queue;

    public AsyncWholeCheckerExecutor(List<WholePresentationChecker> checkers) {
        this.checkers = checkers;
        queue = new LinkedBlockingQueue<>();
    }

    public void enqueueNewPage(PageContent pageContent, LinkURL link, ContentType contentType) {
        WholeExecQueueElement page = new WholeExecQueueElement(pageContent, link, contentType);
        queue.add(page);
    }

    public void finalizeCheckup(TraversalGraph traversalGraph) {
        WholeExecQueueElement finalElement = new WholeExecQueueElement(traversalGraph);
        queue.add(finalElement);
    }

    @Override
    public void run() {
        try {
            WholeExecQueueElement queueElement;
            while (true) {
                queueElement = queue.take();
                //check last element
                if (queueElement.isEndOfQueue()){
                    break;
                }
                //page element is valid
                AbstractCode abstractCode = AbstractCode.createCode(queueElement.getContentType(), queueElement.getLink(), queueElement.getPageContent());
                for (WholePresentationChecker i: checkers){
                    i.addPage(abstractCode);
                }
            }
            //continue to whole presentation check
            for (WholePresentationChecker i: checkers){
                i.finalizeCheckup(queueElement.getTraversalGraph());
            }
        } catch (InterruptedException ex) {
            //interruption means server is shutting down
            //nothing necessary to do
        }
    }
    
}
