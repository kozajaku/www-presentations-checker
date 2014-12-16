package org.presentation.kernel;

import java.io.Serializable;

/**
 * <p>
 * Progress class.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class Progress implements Serializable {

    private final int pageLimit;
    private final int pagesCrawled;

    /**
     * <p>
     * Constructor for Progress.</p>
     *
     * @param pageLimit a int.
     * @param pagesCrawled a int.
     */
    public Progress(int pageLimit, int pagesCrawled) {
        this.pageLimit = pageLimit;
        this.pagesCrawled = pagesCrawled;
    }

    /**
     * <p>
     * percentDone.</p>
     *
     * @return a double.
     */
    public double percentDone() {
        if (pagesCrawled >= pageLimit) {
            return 100.0;
        }
        return (((double) pagesCrawled) / pageLimit) * 100.0;
    }

    /**
     * <p>
     * Getter for the field <code>pageLimit</code>.</p>
     *
     * @return a int.
     */
    public int getPageLimit() {
        return pageLimit;
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
