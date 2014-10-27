package org.presentation.singlepagecontroller;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.MessageProducer;
import org.presentation.utils.Option;
import org.presentation.utils.Stoppable;

/**
 * Interface for every type of single page control.
 *
 * Jindřich Máca
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface SinglePageControllerService extends MessageProducer, Option, Stoppable {

    /**
     * Request for check page.
     *
     * @param contentType Content type of the page
     * @param url Link of the page
     * @param text Content of the page
     */
    void checkPage(ContentType contentType, LinkURL url, PageContent text);

    /**
     * Method quickly response, if page with passed contentType can be checked
     * by this implementation of SinglePageControllerService.
     *
     * @param contentType ContentType of the page
     * @return true if it is applicable; false otherwise
     */
    boolean isApplicable(ContentType contentType);
}
