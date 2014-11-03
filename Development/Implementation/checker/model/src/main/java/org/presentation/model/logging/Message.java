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
    /**
     * Constant <code>MAX_PRIORITY_BOOST=50</code>
     */
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
     * Returns {@link java.lang.String} content of this
     * {@link org.presentation.model.logging.Message}.
     *
     * @return {@link java.lang.String} content of the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets {@link java.lang.String} content of this
     * {@link org.presentation.model.logging.Message}.
     *
     * @param message {@link java.lang.String} content of the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns {@link org.presentation.model.LinkURL} of the page, which this
     * {@link org.presentation.model.logging.Message} releates to.
     *
     * @return {@link org.presentation.model.LinkURL} page of the
     * {@link org.presentation.model.logging.Message}
     */
    public LinkURL getPage() {
        return page;
    }

    /**
     * Sets {@link org.presentation.model.LinkURL} of the page, which this
     * {@link org.presentation.model.logging.Message} releates to.
     *
     * @param page {@link org.presentation.model.LinkURL} page of the
     * {@link org.presentation.model.logging.Message}
     */
    public void setPage(LinkURL page) {
        this.page = page;
    }

    /**
     * Returns {@link org.presentation.model.logging.MsgLocation} of the this
     * {@link org.presentation.model.logging.Message} in page content.
     *
     * @return {@link org.presentation.model.logging.MsgLocation} of the
     * {@link org.presentation.model.logging.Message}
     */
    public MsgLocation getMsgLocation() {
        return msgLocation;
    }

    /**
     * Sets {@link org.presentation.model.logging.MsgLocation} of the this
     * {@link org.presentation.model.logging.Message} in page content.
     *
     * @param msgLocation {@link org.presentation.model.logging.MsgLocation} of
     * the {@link org.presentation.model.logging.Message}
     */
    public void setMsgLocation(MsgLocation msgLocation) {
        this.msgLocation = msgLocation;
    }

    /**
     * Sets {@link org.presentation.model.logging.MessageMapper} by which will
     * be this {@link org.presentation.model.logging.Message} mapped into
     * database.
     *
     * @param mapper {@link org.presentation.model.logging.MessageMapper} of the
     * {@link org.presentation.model.logging.Message}
     */
    public void setIntoMapper(MessageMapper mapper) {
        mapper.setMessage(message);
        mapper.setPage(page);
        mapper.setMsgLocation(msgLocation);
        mapper.setDiscriminator(this.getClass().getName());
        mapper.setPriority(this.getPriority());
    }

    /**
     * Sets {@link org.presentation.model.logging.MessageMapper} by which will
     * be this {@link org.presentation.model.logging.Message} mapped from
     * database.
     *
     * @param mapper {@link org.presentation.model.logging.MessageMapper} of the
     * {@link org.presentation.model.logging.Message}
     */
    public void setFromMapper(MessageMapper mapper) {
        this.message = mapper.getMessage();
        this.page = mapper.getPage();
        this.msgLocation = mapper.getMsgLocation();
        this.priority = mapper.getPriority();
    }

    /**
     * Sets priority of this {@link org.presentation.model.logging.Message} by
     * which will be message sorted. Priority is calculated from parameter and
     * {@link org.presentation.model.logging.Message} default priority.
     *
     * @param priority {@code int} priority of the
     * {@link org.presentation.model.logging.Message}
     */
    public void setPriority(int priority) {
        setPriorityBoost(priority - this.getDefaultPriority());
    }

    /**
     * Sets priority boost of this
     * {@link org.presentation.model.logging.Message}. Priority boost must be
     * from defined interval, else IllegalArgumentException is thrown.
     *
     * @param priorityBoost {@code int} priority boost of the
     * {@link org.presentation.model.logging.Message}
     */
    public void setPriorityBoost(int priorityBoost) {
        if (priorityBoost < -MAX_PRIORITY_BOOST || priorityBoost > MAX_PRIORITY_BOOST) {
            throw new IllegalArgumentException("Invalid message priority set!");
        }
        this.priority = this.getDefaultPriority() + priorityBoost;
    }

    /**
     * Returns priority of this {@link org.presentation.model.logging.Message}
     * by which will be message sorted. If priority is not set returns
     * {@code null}.
     *
     * @return {@link java.lang.Integer} priority of the
     * {@link org.presentation.model.logging.Message} if it is set; {@code null}
     * otherwise
     */
    public int getPriority() {
        return this.priority == null ? this.getDefaultPriority() : this.priority;
    }

    /**
     * Returns default priority of this
     * {@link org.presentation.model.logging.Message}, which should be specified
     * in every type of {@link org.presentation.model.logging.Message} - every
     * class that extends {@link org.presentation.model.logging.Message}.
     *
     * @return default priority of the
     * {@link org.presentation.model.logging.Message} specified in in every type
     * of {@link org.presentation.model.logging.Message}
     */
    public abstract int getDefaultPriority();

}
