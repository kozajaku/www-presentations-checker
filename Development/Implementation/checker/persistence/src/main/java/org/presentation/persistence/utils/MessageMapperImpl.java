package org.presentation.persistence.utils;

import org.presentation.model.LinkURL;
import org.presentation.model.logging.ErrorCode;
import org.presentation.model.logging.MessageMapper;
import org.presentation.model.logging.MsgLocation;
import org.presentation.persistence.model.MessageEntity;

/**
 * Default implementation of {@link org.presentation.model.logging.MessageMapper} interface.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class MessageMapperImpl implements MessageMapper {

    private final MessageEntity messageEntity;

    /**
     * <p>Constructor for MessageMapperImpl.</p>
     *
     * @param messageEntity a {@link org.presentation.persistence.model.MessageEntity} object.
     */
    public MessageMapperImpl(MessageEntity messageEntity) {
        this.messageEntity = messageEntity;
    }

    /** {@inheritDoc} */
    @Override
    public void setMessage(String message) {
        messageEntity.setMessage(message);
    }

    /** {@inheritDoc} */
    @Override
    public void setPage(LinkURL linkURL) {
        messageEntity.setPage(linkURL.getUrl());
    }

    /** {@inheritDoc} */
    @Override
    public void setMsgLocation(MsgLocation msgLocation) {
        if (msgLocation == null) {
            return;
        }
        messageEntity.setRow(msgLocation.getRow());
        messageEntity.setColumn(msgLocation.getColumn());
    }

    /** {@inheritDoc} */
    @Override
    public void setErrorCode(ErrorCode errorCode) {
        messageEntity.setErrorCode(errorCode.getCode());
    }

    /** {@inheritDoc} */
    @Override
    public String getMessage() {
        return messageEntity.getMessage();
    }

    /** {@inheritDoc} */
    @Override
    public LinkURL getPage() {
        return new LinkURL(messageEntity.getPage());
    }

    /** {@inheritDoc} */
    @Override
    public MsgLocation getMsgLocation() {
        if (messageEntity.getRow() == null && messageEntity.getColumn() == null) {
            return null;
        }
        return new MsgLocation(messageEntity.getRow(), messageEntity.getColumn());
    }

    /** {@inheritDoc} */
    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(messageEntity.getErrorCode());
    }

    /** {@inheritDoc} */
    @Override
    public void setDiscriminator(String discriminator) {
        messageEntity.setDiscriminator(discriminator);
    }

    /** {@inheritDoc} */
    @Override
    public void setPriority(Integer priority) {
        messageEntity.setPriority(priority);
    }

    /** {@inheritDoc} */
    @Override
    public Integer getPriority() {
        return messageEntity.getPriority();
    }

}
