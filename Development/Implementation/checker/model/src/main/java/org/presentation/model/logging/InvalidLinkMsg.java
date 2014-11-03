package org.presentation.model.logging;

/**
 * Represents invalid link type of abstract class
 * {@link org.presentation.model.logging.Message}, which extends class
 * {@link org.presentation.model.logging.ErrorMsg} for
 * {@link org.presentation.model.logging.ResponseCode}. This class is used for
 * invalid links in web pages and HTTP status codes, which returns HTTP request
 * sended to them.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class InvalidLinkMsg extends ErrorMsg {

    //HTTP status code, which make the link invalid
    private ResponseCode errorCode;

    /**
     * Returns {@link org.presentation.model.logging.ResponseCode} of this
     * {@link org.presentation.model.logging.InvalidLinkMsg}.
     *
     * @return {@link org.presentation.model.logging.ResponseCode} of invalid
     * link
     */
    public ResponseCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets {@link org.presentation.model.logging.ResponseCode} of this
     * {@link org.presentation.model.logging.InvalidLinkMsg}.
     *
     * @param errorCode {@link org.presentation.model.logging.ResponseCode} of
     * invalid link
     */
    public void setErrorCode(ResponseCode errorCode) {
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
