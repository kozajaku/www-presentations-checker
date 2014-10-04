package org.presentation.webcrawler;

import java.util.List;
import org.presentation.utils.Stoppable;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.model.logging.MessageProducer;

/**
 * Interface for WebCrawler service. Every module, that want to serve as
 * WebCrawler service provider must register implementation of this service.
 *
 * @author Adam Kuger
 * @version 1.0
 */
public interface CrawlerService extends MessageProducer, Stoppable {

    /**
     *
     * @param url
     * @param maximalDepth
     * @param pageLimit
     * @param observer Observer object, into which the CrawlerService
     * implementation will send its results.
     * @param addHeaders Additional headers which are going to be used during fetching
     * pages from remote web server.
     * @param requestTimeout
     * @param allowedDomains
     */
    void startBrowsing(LinkURL url, int maximalDepth, int pageLimit, PageCrawlingObserver observer, List<Domain> allowedDomains, int requestTimeout, List<Header> addHeaders);

    CrawlingState getCrawlingState();

}
