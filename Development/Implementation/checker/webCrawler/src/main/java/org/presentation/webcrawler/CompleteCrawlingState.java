package org.presentation.webcrawler;

/**
 * @author Adam Kugler
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public enum CompleteCrawlingState {
	ENDED_BY_DEPTH,
	ENDED_BY_PAGE_LIMIT,
	STOPPED_BY_USER,
	/**
	 * Error in web crawling (server disconnected, etc...)
	 */
	ERROR,
	/**
	 * Unknown web crawler completion state - should not be used.
	 */
	UNKNOWN,
        WEB_CRAWLED
}