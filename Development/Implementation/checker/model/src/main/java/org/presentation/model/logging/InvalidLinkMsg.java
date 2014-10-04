package org.presentation.model.logging;

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

    @Override
    public void setFromMapper(MessageMapper mapper) {
        super.setFromMapper(mapper);
        errorCode = mapper.getErrorCode();
    }

    @Override
    public void setIntoMapper(MessageMapper mapper) {
        super.setIntoMapper(mapper);
        mapper.setErrorCode(errorCode);
    }

}
