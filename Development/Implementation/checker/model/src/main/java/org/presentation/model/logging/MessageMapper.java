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
    
    void setPriority(Integer priority);
    
    String getMessage();
    
    LinkURL getPage();
    
    MsgLocation getMsgLocation();
    
    ErrorCode getErrorCode();
    
    Integer getPriority();
    
    void setDiscriminator(String discriminator);
}
