package org.presentation.model;

/**
 * This class represents header in http or https request.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class Header {

    private final String key;
    private final String value;

    /**
     * <p>
     * Constructor for Header.</p>
     *
     * @param key Header key
     * @param value Header value
     */
    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * <p>
     * Getter for the field <code>key</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getKey() {
        return key;
    }

    /**
     * <p>
     * Getter for the field <code>value</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getValue() {
        return value;
    }

}
