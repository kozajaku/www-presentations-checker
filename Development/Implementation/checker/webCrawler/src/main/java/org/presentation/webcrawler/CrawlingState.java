package org.presentation.webcrawler;

/**
 * This class represents state of crawling.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public class CrawlingState {

    private int pagesCrawled;
    private boolean done;

    /**
     * <p>
     * Constructor for CrawlingState.</p>
     */
    public CrawlingState() {
        pagesCrawled = 0;
        done = false;
    }

    /**
     * Sets done as true.
     */
    public void done() {
        done = true;
    }

    /**
     * Increase count of crawled pages.
     */
    public void incPagesCrawled() {
        pagesCrawled++;
    }

    /**
     * <p>
     * isDone.</p>
     *
     * @return a boolean.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * <p>
     * Getter for the field <code>pagesCrawled</code>.</p>
     *
     * @return a int.
     */
    public int getPagesCrawled() {
        return pagesCrawled;
    }
}
