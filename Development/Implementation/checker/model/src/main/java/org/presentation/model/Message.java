package org.presentation.model;

/**
 *
 * @author Jindřich Máca
 */
public abstract class Message {

    private String message;
    private LinkURL page;
    private MsgLocation msgLocation;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LinkURL getPage() {
        return page;
    }

    public void setPage(LinkURL page) {
        this.page = page;
    }

    public MsgLocation getMsgLocation() {
        return msgLocation;
    }

    public void setMsgLocation(MsgLocation msgLocation) {
        this.msgLocation = msgLocation;
    }

}
