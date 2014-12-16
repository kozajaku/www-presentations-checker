package org.presentation.kernel.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.graphgenerator.GraphGeneratorQueue;
import org.presentation.graphgenerator.GraphResult;
import org.presentation.kernel.Progress;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.Message;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MsgReport;
import org.presentation.persistence.business.PersistenceFacade;
import org.presentation.persistence.model.CheckState;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.ChosenOption;
import org.presentation.persistence.model.Graph;
import org.presentation.singlepagecontroller.SinglePageController;
import org.presentation.utils.OptionContainer;
import org.presentation.utils.Stoppable;
import org.presentation.webcrawler.CompleteCrawlingState;
import org.presentation.webcrawler.CrawlerService;
import org.presentation.webcrawler.CrawlingState;
import org.presentation.webcrawler.PageCrawlingObserver;
import org.presentation.wholepresentationcontroller.WholePresentationController;

/**
 * Class realizes flow of execution of one single checkup. Checking executor has
 * provided one thread to work but it can also create new threads (or submodules
 * can) if it is necessary. Note that pool of threads is limited and so one
 * checking should NOT use more threads then 5 at one time.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class CheckingExecutor implements PageCrawlingObserver, Stoppable {

    @EJB
    private PersistenceFacade persistenceFacade;

    @Inject
    private MessageLoggerContainer messageLoggerContainer;

    @Inject
    private CrawlerService crawlerService;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;//for debug purposes only

    private Checkup checkup;

    private Future<List<GraphResult>> futureGraphResults;

    @Inject
    private SinglePageController singlePageController;
    @Inject
    private WholePresentationController wholePresentationController;

    @EJB
    private GraphGeneratorQueue graphGenerator;

    /**
     * Simply start checking of
     * {@link org.presentation.persistence.model.Checkup} passed as parameter.
     * Method has very long duration and is blocking.
     *
     * @param checkup A {@link org.presentation.persistence.model.Checkup}
     * object representing information about check that should be executed by
     * this {@link org.presentation.kernel.impl.CheckingExecutor}
     * @return {@code true} if checking ended successfully, {@code false} if
     * ended with error
     */
    @SuppressWarnings("UseSpecificCatch")
    public boolean startChecking(Checkup checkup) {
        this.checkup = checkup;
        try {
            LOG.info("Starting new checking");
            //fetch initialized checkup
            crawlerService.offerMsgLoggerContainer(messageLoggerContainer);
            //initialize controllers
            OptionContainer oc = new OptionContainer();
            List<ChosenOption> options = persistenceFacade.findCheckupOptions(checkup);
            for (ChosenOption i : options) {
                oc.addOption(i.getIdOption());
            }
            crawlerService.initializeCrawler(oc);
            singlePageController.initializeControllers(oc);
            singlePageController.offerMsgLoggerContainer(messageLoggerContainer);
            wholePresentationController.initializeControllers(oc);
            wholePresentationController.offerMsgLoggerContainer(messageLoggerContainer);
            //this method blocks to the end of web crawling
            crawlerService.startBrowsing(new LinkURL(checkup.getStartPoint()),
                    checkup.getMaxDepth(), checkup.getPageLimit(), this,
                    persistenceFacade.findCheckupDomains(checkup),
                    checkup.getCheckingInterval(),
                    persistenceFacade.findCheckupHeaders(checkup));
            //now crawling is done
            CrawlingState state = crawlerService.getCrawlingState();
            LOG.log(Level.INFO, "Crawling of checkup with id {0} has ended. Pages crawled: {1}", new Object[]{checkup.getIdCheckup(), state.getPagesCrawled()});
            wholePresentationController.getExecutionFuture().get();
            //persist results to database
            MsgReport report = messageLoggerContainer.generateMsgReport();
            for (Map.Entry<String, List<Message>> i : report.getMsgGroups().entrySet()) {
                persistenceFacade.addMessagesToCheckup(checkup, i.getValue(), i.getKey());
            }
            //wait for graph results and persist them to database
            List<GraphResult> graphResults = futureGraphResults.get();
            List<Graph> graphs = new ArrayList<>();
            Graph tmp;
            for (GraphResult i : graphResults) {
                tmp = new Graph();
                tmp.setGraphType(i.getResultId());
                tmp.setOutput(i.getResultAsCode());
                graphs.add(tmp);
            }
            persistenceFacade.addGraphsToCheckup(checkup, graphs);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processOnePage(LinkURL pageUrl, PageContent pageSourceCode, ContentType contentType) {
        LOG.log(Level.INFO, "Processing page with url: {0}", pageUrl.getUrl());
        singlePageController.checkPage(contentType, pageUrl, pageSourceCode);//in future releases non-blocking
        wholePresentationController.addPage(pageSourceCode, pageUrl, contentType);//this is be non-blocking op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void crawlingDone(TraversalGraph traversalGraph, CompleteCrawlingState crawlingState) {
        LOG.log(Level.INFO, "Called crawling done method. State: {0}", crawlingState.name());
        //asynchronous generating graphs
        futureGraphResults = graphGenerator.drawGraph(traversalGraph);
        //asynchronous finishing wholePresentationController
        wholePresentationController.checkPresentation(traversalGraph);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopChecking() {
        //this method must be non blocking 
        checkup.setState(CheckState.STOPPED_AFTER_START);
        crawlerService.stopChecking();
    }

    /**
     * <p>
     * getCheckupProgress.</p>
     *
     * @return a {@link org.presentation.kernel.Progress} object.
     */
    public Progress getCheckupProgress() {
        int pageLimit = checkup.getPageLimit();
        int crawled = crawlerService.getCrawlingState().getPagesCrawled();
        return new Progress(pageLimit, crawled);
    }
}
