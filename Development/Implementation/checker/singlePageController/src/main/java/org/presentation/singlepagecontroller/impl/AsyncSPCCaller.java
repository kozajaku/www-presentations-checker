package org.presentation.singlepagecontroller.impl;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.singlepagecontroller.SinglePageControllerService;

/**
 *
 * @author radio.koza
 */
public class AsyncSPCCaller implements Runnable{
    
    private final SinglePageControllerService singlePageControllerService;
    private final ContentType contentType;
    private final LinkURL linkURL;
    private final PageContent pageContent;

    public AsyncSPCCaller(ContentType contentType, LinkURL linkURL, PageContent pageContent, SinglePageControllerService singlePageControllerService) {
        this.singlePageControllerService = singlePageControllerService;
        this.contentType = contentType;
        this.linkURL = linkURL;
        this.pageContent = pageContent;
    }

    /**
     * Method is called in new thread. It just only call checkPage of specified
     * spcService and it passes specified arguments to it.
     */
    @Override
    public void run() {
        singlePageControllerService.checkPage(contentType, linkURL, pageContent);
    }
    
}
