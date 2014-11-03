package org.presentation.webcrawler;

import java.util.List;
import org.presentation.utils.Stoppable;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.model.logging.MessageProducer;
import org.presentation.utils.OptionContainer;

/**
 * Interface for web crawler service. Every module, that want to serve as web
 * crawler service provider must register implementation of this service.
 *
 * @author Adam Kuger
 * @version 1.0
 */
public interface CrawlerService extends MessageProducer, Stoppable {

    /**
     * Initialize web crawler service by using options set by user when creating
     * new checking request.
     *
     * @param options {@link org.presentation.utils.OptionContainer} object
     * representing options set by user
     */
    void initializeCrawler(OptionContainer options);

    /**
     * Starts browsing. Sends messages about valid/invalid links. Modifies
     * crawling state. Creates
     * {@link org.presentation.model.graph.TraversalGraph}.
     *
     * @param url starting URL
     * @param maximalDepth Crawling depth which schould not be overrun.
     * @param pageLimit Number of fully crawled pages which schould not be
     * overrun.
     * @param observer Observer object, into which the CrawlerService
     * implementation will send its results.
     * @param addHeaders Additional headers which are going to be used during
     * fetching pages from remote web server.
     * @param requestTimeout Time in ms between individual get requests.
     * @param allowedDomains List of allowed domains on that can crawler
     * continue in his job. Link from other domain will be just checked.
     */
    void startBrowsing(LinkURL url, int maximalDepth, int pageLimit, PageCrawlingObserver observer, List<Domain> allowedDomains, int requestTimeout, List<Header> addHeaders);

    /**
     * <p>
     * getCrawlingState.</p>
     *
     * @return a {@link org.presentation.webcrawler.CrawlingState} object.
     */
    CrawlingState getCrawlingState();

}
