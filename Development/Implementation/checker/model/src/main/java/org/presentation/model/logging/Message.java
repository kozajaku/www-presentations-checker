package org.presentation.model.logging;

import org.presentation.model.LinkURL;

/**
 * Abstract class representing common composition of message.
 *
 * @author Jindřich Máca
 */
public abstract class Message {

    /**
     * Maximal priority constant value.
     */
    protected static final int MAX_PRIORITY_BOOST = 50;

    /**
     * Concrete priority of the message.
     */
    private Integer priority = null;
    /**
     * Text of the message.
     */
    private String message;
    /**
     * URL of the page, which message releates to.
     */
    private LinkURL page;
    /**
     * Location of the message content.
     */
    private MsgLocation msgLocation;

    /**
     * Returns the text of the message.
     *
     * @return Text of the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the text of the message.
     *
     * @param message Text of the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the URL of the message, which message releates to.
     *
     * @return URL of the message, which message releates to.
     */
    public LinkURL getPage() {
        return page;
    }

    /**
     * Sets the URL of the message, which message releates to.
     *
     * @param page URL of the message, which message releates to.
     */
    public void setPage(LinkURL page) {
        this.page = page;
    }

    /**
     * Returns the location of the message content.
     *
     * @return Location of the message content.
     */
    public MsgLocation getMsgLocation() {
        return msgLocation;
    }

    /**
     * Sets the location of the message content.
     *
     * @param msgLocation Location of the message content.
     */
    public void setMsgLocation(MsgLocation msgLocation) {
        this.msgLocation = msgLocation;
    }

    /**
     * Sets database mapper by which will be messages mapped to database.
     *
     * @param mapper Database mapper.
     */
    public void setIntoMapper(MessageMapper mapper) {
        mapper.setMessage(message);
        mapper.setPage(page);
        mapper.setMsgLocation(msgLocation);
        mapper.setDiscriminator(this.getClass().getName());
    }

    /**
     * Sets database mapper by which will be messages mapped from database.
     *
     * @param mapper Database mapper.
     */
    public void setFromMapper(MessageMapper mapper) {
        this.message = mapper.getMessage();
        this.page = mapper.getPage();
        this.msgLocation = mapper.getMsgLocation();
    }

    /**
     * Sets priority of the message.
     *
     * @param priority Message priority.
     */
    public void setPriority(int priority) {
        setPriorityBoost(priority - this.getDefaultPriority());
    }

    /**
     * Sets priority boost for message.
     *
     * @param priorityBoost Message priority boost.
     */
    public void setPriorityBoost(int priorityBoost) {
        if (priorityBoost < -MAX_PRIORITY_BOOST || priorityBoost > MAX_PRIORITY_BOOST) {
            throw new IllegalArgumentException("Invalid message priority set!");
        }
        this.priority = this.getDefaultPriority() + priorityBoost;
    }

    /**
     * Returns message priority.
     *
     * @return Message priority.
     */
    public int getPriority() {
        return this.priority == null ? this.getDefaultPriority() : this.priority;
    }

    /**
     * Returns default message priority.
     *
     * @return Default message priority.
     */
    public abstract int getDefaultPriority();

}
