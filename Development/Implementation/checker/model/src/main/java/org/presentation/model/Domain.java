package org.presentation.model;

import java.io.Serializable;

/**
 * This class represents web domain.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public class Domain implements Serializable {

    private final String domain;

    /**
     * <p>
     * Constructor for Domain.</p>
     *
     * @param domain a {@link java.lang.String} object.
     */
    public Domain(String domain) {
        this.domain = domain;
    }

    /**
     * <p>
     * Getter for the field <code>domain</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDomain() {
        return domain;
    }

}
