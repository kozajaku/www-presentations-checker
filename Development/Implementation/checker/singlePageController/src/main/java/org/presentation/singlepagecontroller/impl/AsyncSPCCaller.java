package org.presentation.singlepagecontroller.impl;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.singlepagecontroller.SinglePageChecker;

/**
 * <p>
 * AsyncSPCCaller class.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class AsyncSPCCaller implements Runnable {

    private final SinglePageChecker singlePageControllerService;
    private final ContentType contentType;
    private final LinkURL linkURL;
    private final PageContent pageContent;

    /**
     * <p>
     * Constructor for AsyncSPCCaller.</p>
     *
     * @param contentType a {@link org.presentation.model.ContentType} object.
     * @param linkURL a {@link org.presentation.model.LinkURL} object.
     * @param pageContent a {@link org.presentation.model.PageContent} object.
     * @param singlePageControllerService a
     * {@link org.presentation.singlepagecontroller.SinglePageChecker}
     * object.
     */
    public AsyncSPCCaller(ContentType contentType, LinkURL linkURL, PageContent pageContent, SinglePageChecker singlePageControllerService) {
        this.singlePageControllerService = singlePageControllerService;
        this.contentType = contentType;
        this.linkURL = linkURL;
        this.pageContent = pageContent;
    }

    /**
     * {@inheritDoc}
     *
     * Method is called in new thread. It just only call checkPage of specified
     * spcService and it passes specified arguments to it.
     */
    @Override
    public void run() {
        singlePageControllerService.checkPage(contentType, linkURL, pageContent);
    }

}
