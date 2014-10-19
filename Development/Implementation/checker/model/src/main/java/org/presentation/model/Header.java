package org.presentation.model;

/**
 * This class represents header in http or https request.
 * @author radio.koza
 */
public class Header {

    private final String key;
    private final String value;
    /**
     * 
     * @param key Header key
     * @param value Header value
     */
    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
