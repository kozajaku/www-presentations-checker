package org.presentation.model.logging;

/**
 * Represents invalid link type of abstract class Message, which is
 * specification of class Error Message.
 *
 * @author Jindřich Máca
 * @version 1.0-SNAPSHOT
 */
public class InvalidLinkMsg extends ErrorMsg {

    /**
     * HTTP error code, which make the link invalid.
     */
    private ErrorCode errorCode;

    /**
     * Returns HTTP error code of invalid link.
     *
     * @return HTTP error code of invalid link.
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the HTTP error code of invalid link.
     *
     * @param errorCode HTTP error code.
     */
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFromMapper(MessageMapper mapper) {
        super.setFromMapper(mapper);
        errorCode = mapper.getErrorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIntoMapper(MessageMapper mapper) {
        super.setIntoMapper(mapper);
        mapper.setErrorCode(errorCode);
    }

}
