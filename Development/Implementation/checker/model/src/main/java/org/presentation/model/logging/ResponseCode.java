package org.presentation.model.logging;

/**
 * Represents HTTP state code, but is used to save only codes that represents
 * some kind of error.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class ResponseCode {

    //Integer representation of the HTTP response code
    private Integer code;

    /**
     * Creates new instance of {@link ErrorCode}.
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
