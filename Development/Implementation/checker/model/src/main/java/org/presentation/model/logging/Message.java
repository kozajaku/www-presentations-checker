package org.presentation.model.logging;

import org.presentation.model.LinkURL;

/**
 * Abstract class representing common composition of messages, which is used for
 * representing all outputs from all controls.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public abstract class Message {

    //Maximal priority constant value
    protected static final int MAX_PRIORITY_BOOST = 50;
    //Concrete priority of the message
    private Integer priority = null;
    //Text of the message
    private String message;
    //URL of the page, which message releates to
    private LinkURL page;
    //Location of the message content
    private MsgLocation msgLocation;

    /**
     * Returns {@link String} content of this {@link Message}.
     *
     * @return {@link String} content of the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets {@link String} content of this {@link Message}.
     *
     * @param message {@link String} content of the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns {@link LinkURL} of the page, which this {@link Message} releates
     * to.
     *
     * @return {@link LinkURL} page of the {@link Message}
     */
    public LinkURL getPage() {
        return page;
    }

    /**
     * Sets {@link LinkURL} of the page, which this {@link Message} releates to.
     *
     * @param page {@link LinkURL} page of the {@link Message}
     */
    public void setPage(LinkURL page) {
        this.page = page;
    }

    /**
     * Returns {@link MsgLocation} of the this {@link Message} in page content.
     *
     * @return {@link MsgLocation} of the {@link Message}
     */
    public MsgLocation getMsgLocation() {
        return msgLocation;
    }

    /**
     * Sets {@link MsgLocation} of the this {@link Message} in page content.
     *
     * @param msgLocation {@link MsgLocation} of the {@link Message}
     */
    public void setMsgLocation(MsgLocation msgLocation) {
        this.msgLocation = msgLocation;
    }

    /**
     * Sets {@link MessageMapper} by which will be this {@link Message} mapped
     * into database.
     *
     * @param mapper {@link MessageMapper} of the {@link Message}
     */
    public void setIntoMapper(MessageMapper mapper) {
        mapper.setMessage(message);
        mapper.setPage(page);
        mapper.setMsgLocation(msgLocation);
        mapper.setDiscriminator(this.getClass().getName());
        mapper.setPriority(this.getPriority());
    }

    /**
     * Sets {@link MessageMapper} by which will be this {@link Message} mapped
     * from database.
     *
     * @param mapper {@link MessageMapper} of the {@link Message}
     */
    public void setFromMapper(MessageMapper mapper) {
        this.message = mapper.getMessage();
        this.page = mapper.getPage();
        this.msgLocation = mapper.getMsgLocation();
        this.priority = mapper.getPriority();
    }

    /**
     * Sets priority of this {@link Message} by which will be message sorted.
     * Priority is calculated from parameter and {@link Message} default
     * priority.
     *
     * @param priority {@code int} priority of the {@link Message}
     */
    public void setPriority(int priority) {
        setPriorityBoost(priority - this.getDefaultPriority());
    }

    /**
     * Sets priority boost of this {@link Message}. Priority boost must be from
     * defined interval, else IllegalArgumentException is thrown.
     *
     * @param priorityBoost {@code int} priority boost of the {@link Message}
     */
    public void setPriorityBoost(int priorityBoost) {
        if (priorityBoost < -MAX_PRIORITY_BOOST || priorityBoost > MAX_PRIORITY_BOOST) {
            throw new IllegalArgumentException("Invalid message priority set!");
        }
        this.priority = this.getDefaultPriority() + priorityBoost;
    }

    /**
     * Returns priority of this {@link Message} by which will be message sorted.
     * If priority is not set returns {@code null}.
     *
     * @return {@link Integer} priority of the {@link Message} if it is set;
     * {@code null} otherwise
     */
    public int getPriority() {
        return this.priority == null ? this.getDefaultPriority() : this.priority;
    }

    /**
     * Returns default priority of this {@link Message}, which should be
     * specified in every type of {@link Message} - every class that extends
     * {@link Message}.
     *
     * @return default priority of the {@link Message} specified in in every
     * type of {@link Message}
     */
    public abstract int getDefaultPriority();

}
