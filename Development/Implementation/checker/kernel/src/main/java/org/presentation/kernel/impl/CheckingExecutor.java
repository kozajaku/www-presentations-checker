package org.presentation.kernel.impl;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.persistence.business.PersistenceFacade;
import org.presentation.utils.Stoppable;
import org.presentation.webcrawler.CompleteCrawlingState;
import org.presentation.webcrawler.CrawlerService;
import org.presentation.webcrawler.PageCrawlingObserver;

/**
 *
 * @author radio.koza
 */
@Dependent
public class CheckingExecutor implements PageCrawlingObserver, Stoppable{

//    @EJB
//    private PersistenceFacade persistenceFacade;
    
    @Inject
    private MessageLoggerContainer messageLoggerContainer;
    
    @Inject
    private CrawlerService crawlerService;
    
    @Inject
    private Logger LOG;//for debug purposes only
    
//    @Inject
//    private SinglePageController singlePageController;
    
//    @Inject
//    private WholePresentationController wholePresentationController;
    
//    @EJB
//    private GraphGeneratorQueue graphGenerator;
    
    public void startChecking(){
        //TODO implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void processOnePage(LinkURL pageUrl, PageContent pageSourceCode, ContentType contentType) {
        //TODO implement
        
        //consider it done...
    }

    @Override
    public void crawlingDone(TraversalGraph traversalGraph, CompleteCrawlingState crawlingState) {
        //TODO implement
        
        //consider it done...
    }

    @Override
    public void stopChecking() {
        //TODO implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    
}
