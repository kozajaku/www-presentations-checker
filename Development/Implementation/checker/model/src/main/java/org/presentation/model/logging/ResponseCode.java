package org.presentation.model.logging;

/**
 * Represents HTTP response code.
 *
 * @author radio.koza
 */
public class ResponseCode {

    //Integer representation of the HTTP error code
    private Integer code;

    /**
     * Creates new instance of {@link ResponseCode}.
     *
     * @param code {@link Integer} representation of the HTTP status code
     */
    public ResponseCode(Integer code) {
        this.code = code;
    }

    /**
     * Returns the {@link Integer} representation of {@link ResponseCode}.
     *
     * @return {@link Integer} representation of the HTTP status code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Sets the {@link Integer} representation of {@link ResponseCode}.
     *
     * @param code {@link Integer} representation of the HTTP status code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Returns the {@link String} explanation of {@link ResponseCode}.
     *
     * @return {@link String} explanation of HTTP status code
     */
    public String explain() {
        //TODO: Define messages for different status codes.
        return null;
    }
}
