package org.presentation.kernel.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
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
import org.presentation.utils.Stoppable;
import org.presentation.webcrawler.CompleteCrawlingState;
import org.presentation.webcrawler.CrawlerService;
import org.presentation.webcrawler.CrawlingState;
import org.presentation.webcrawler.PageCrawlingObserver;

/**
 *
 * @author radio.koza
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
    
//    @Inject
//    private SinglePageController singlePageController;
//    @Inject
//    private WholePresentationController wholePresentationController;
//    @EJB
//    private GraphGeneratorQueue graphGenerator;
    public void startChecking(Checkup checkup) {
        this.checkup = checkup;
        try {
            LOG.info("Starting new checking");
            //fetch initialized checkup
            crawlerService.offerMsgLoggerContainer(messageLoggerContainer);
            //this method blocks to the end of web crawling
            crawlerService.startBrowsing(new LinkURL(checkup.getStartPoint()),
                    checkup.getMaxDepth(), checkup.getPageLimit(), this,
                    persistenceFacade.findCheckupDomains(checkup),
                    checkup.getCheckingInterval(),
                    persistenceFacade.findCheckupHeaders(checkup));
            //now crawling is done
            CrawlingState state = crawlerService.getCrawlingState();
            LOG.log(Level.INFO, "Crawling of checkup with id {0} has ended. Pages crawled: {1}", new Object[]{checkup.getIdCheckup(), state.getPagesCrawled()});
            //TODO wait for controllers
            //TODO generate graphs
            //persist results to database
            MsgReport report = messageLoggerContainer.generateMsgReport();
            for (Map.Entry<String, List<Message>> i : report.getMsgGroups().entrySet()) {
                persistenceFacade.addMessagesToDomain(checkup, i.getValue(), i.getKey());
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processOnePage(LinkURL pageUrl, PageContent pageSourceCode, ContentType contentType) {
        LOG.log(Level.INFO, "Processing page with url: {0}", pageUrl.getUrl());
        //TODO implement

        //consider it done...
    }

    @Override
    public void crawlingDone(TraversalGraph traversalGraph, CompleteCrawlingState crawlingState) {
        LOG.log(Level.INFO, "Called crawling done method. State: {0}", crawlingState.name());
        //TODO implement

        //consider it done...
    }

    @Override
    public void stopChecking() {
        //this method must be non blocking 
        checkup.setState(CheckState.STOPPED_AFTER_START);
        crawlerService.stopChecking();
    }

}
