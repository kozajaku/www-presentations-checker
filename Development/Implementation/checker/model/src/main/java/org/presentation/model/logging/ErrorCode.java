package org.presentation.model.logging;

/**
 *
 * @author Jindřich Máca
 */
public class ErrorCode {

    private Integer code;

    public ErrorCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String explain() {
        //TODO: Define messages for different error codes.
        return null;
    }
}
