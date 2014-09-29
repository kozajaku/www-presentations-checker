package org.presentation.model.logging;

import org.presentation.model.logging.ErrorCode;

/**
 *
 * @author Jindřich Máca
 */
public class InvalidLinkMsg extends ErrorMsg {

    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
