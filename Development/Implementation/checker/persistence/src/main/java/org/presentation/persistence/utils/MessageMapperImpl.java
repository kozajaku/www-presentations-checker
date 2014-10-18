package org.presentation.persistence.utils;

import org.presentation.model.LinkURL;
import org.presentation.model.logging.ErrorCode;
import org.presentation.model.logging.MessageMapper;
import org.presentation.model.logging.MsgLocation;
import org.presentation.persistence.model.MessageEntity;

/**
 *
 * @author radio.koza
 */
public class MessageMapperImpl implements MessageMapper {

    private final MessageEntity messageEntity;

    public MessageMapperImpl(MessageEntity messageEntity) {
        this.messageEntity = messageEntity;
    }

    @Override
    public void setMessage(String message) {
        messageEntity.setMessage(message);
    }

    @Override
    public void setPage(LinkURL linkURL) {
        messageEntity.setPage(linkURL.getUrl());
    }

    @Override
    public void setMsgLocation(MsgLocation msgLocation) {
        if (msgLocation == null) {
            return;
        }
        messageEntity.setRow(msgLocation.getRow());
        messageEntity.setColumn(msgLocation.getColumn());
    }

    @Override
    public void setErrorCode(ErrorCode errorCode) {
        messageEntity.setErrorCode(errorCode.getCode());
    }

    @Override
    public String getMessage() {
        return messageEntity.getMessage();
    }

    @Override
    public LinkURL getPage() {
        return new LinkURL(messageEntity.getPage());
    }

    @Override
    public MsgLocation getMsgLocation() {
        if (messageEntity.getRow() == null && messageEntity.getColumn() == null) {
            return null;
        }
        return new MsgLocation(messageEntity.getRow(), messageEntity.getColumn());
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(messageEntity.getErrorCode());
    }

    @Override
    public void setDiscriminator(String discriminator) {
        messageEntity.setDiscriminator(discriminator);
    }

    @Override
    public void setPriority(Integer priority) {
        messageEntity.setPriority(priority);
    }

    @Override
    public Integer getPriority() {
        return messageEntity.getPriority();
    }

}
