package org.presentation.model.logging;

/**
 * Represents invalid link type of abstract class {@link Message}, which extends
 * class {@link ErrorMsg} for {@link ErrorCode}. This class is used for invalid
 * links in web pages and HTTP status codes, which returns HTTP request sended
 * to them.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class InvalidLinkMsg extends ErrorMsg {

    //HTTP status code, which make the link invalid
    private ErrorCode errorCode;

    /**
     * Returns {@link ErrorCode} of this {@link InvalidLinkMsg}.
     *
     * @return {@link ErrorCode} of invalid link
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets {@link ErrorCode} of this {@link InvalidLinkMsg}.
     *
     * @param errorCode {@link ErrorCode} of invalid link
     */
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
