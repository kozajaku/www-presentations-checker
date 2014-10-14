package org.presentation.webcrawler;

/**
 * This class represents state of crawling when it is done.
 * @author Adam Kugler
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public enum CompleteCrawlingState {
	/**
         * Maximal depth reached.
         */
        ENDED_BY_DEPTH,
        /**
         * Page limit reached.
         */
	ENDED_BY_PAGE_LIMIT,
        /**
         * User stopped control.
         */
	STOPPED_BY_USER,
	/**
	 * Error in web crawling (server disconnected, etc...).
	 */
	ERROR,
	/**
	 * Unknown web crawler completion state - should not be used.
	 */
	UNKNOWN,
        /**
         * Web was completely crawled.
         */
        WEB_CRAWLED
}