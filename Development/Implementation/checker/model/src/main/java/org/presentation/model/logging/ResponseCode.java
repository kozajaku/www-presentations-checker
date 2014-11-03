package org.presentation.model.logging;

/**
 * Represents HTTP response code.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class ResponseCode {

    //Integer representation of the HTTP error code
    private Integer code;

    /**
     * Creates new instance of
     * {@link org.presentation.model.logging.ResponseCode}.
     *
     * @param code {@link java.lang.Integer} representation of the HTTP status
     * code
     */
    public ResponseCode(Integer code) {
        this.code = code;
    }

    /**
     * Returns the {@link java.lang.Integer} representation of
     * {@link org.presentation.model.logging.ResponseCode}.
     *
     * @return {@link java.lang.Integer} representation of the HTTP status code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Sets the {@link java.lang.Integer} representation of
     * {@link org.presentation.model.logging.ResponseCode}.
     *
     * @param code {@link java.lang.Integer} representation of the HTTP status
     * code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Returns the {@link java.lang.String} explanation of
     * {@link org.presentation.model.logging.ResponseCode}.
     *
     * @return {@link java.lang.String} explanation of HTTP status code
     */
    public String explain() {
        //TODO: Define messages for different status codes.
        return null;
    }
}
