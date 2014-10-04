package org.presentation.model.logging;

import org.presentation.model.LinkURL;

/**
 *
 * @author radio.koza
 */
public interface MessageMapper {
    void setMessage(String message);
    
    void setPage(LinkURL linkURL);
    
    void setMsgLocation(MsgLocation msgLocation);
    
    void setErrorCode(ErrorCode errorCode);
    
    String getMessage();
    
    LinkURL getPage();
    
    MsgLocation getMsgLocation();
    
    ErrorCode getErrorCode();
    
    void setDiscriminator(String discriminator);
}
