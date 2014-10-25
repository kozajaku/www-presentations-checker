package org.presentation.model.logging;

/**
 * Represents HTTP error code.
 *
 * @author Jindřich Máca
 * @version 1.0-SNAPSHOT
 */
public class ErrorCode {

    /**
     * Integer representation of the HTTP error code.
     */
    private Integer code;

    /**
     * Constructs the HTTP error code.
     *
     * @param code Integer representation of the HTTP error code.
     */
    public ErrorCode(Integer code) {
        this.code = code;
    }

    /**
     * Returns the integer representation of the HTTP error code.
     *
     * @return Integer representation of the HTTP error code.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Sets the integer representation of the HTTP error code.
     *
     * @param code Integer representation of the HTTP error code.
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * Returns the text explanation of HTTP error code.
     *
     * @return Text explanation of HTTP error code.
     */
    public String explain() {
        //TODO: Define messages for different error codes.
        return null;
    }
}
