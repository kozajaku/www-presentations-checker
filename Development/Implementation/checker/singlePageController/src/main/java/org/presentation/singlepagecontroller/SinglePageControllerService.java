package org.presentation.singlepagecontroller;

import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.MessageProducer;
import org.presentation.utils.Option;
import org.presentation.utils.Stoppable;

/**
 * Interface for every type of single page control.
 *
 * Jindřich Máca
 */
public interface SinglePageControllerService extends MessageProducer, Option, Stoppable {

    /**
     * Request for check page.
     *
     * @param url Link of the page.
     * @param text Content of the page.
     */
    public abstract void checkPage(LinkURL url, PageContent text);
}
