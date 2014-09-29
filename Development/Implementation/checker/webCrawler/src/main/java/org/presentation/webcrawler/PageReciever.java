package org.presentation.webcrawler;

import org.presentation.model.LinkURL;
import org.presentation.model.MessageProducer;

/**
 *
 * @author Jindřich Máca
 */
public interface PageReciever extends MessageProducer {

    public ReceiverResponse getPage(LinkURL link);
}
