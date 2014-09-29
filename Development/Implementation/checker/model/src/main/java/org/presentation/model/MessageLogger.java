package org.presentation.model;

import java.util.List;

/**
 *
 * @author Jindřich Máca
 */
public class MessageLogger {

    private List<Message> messages;
    private final String resource;

    public MessageLogger(String resource) {
        this.resource = resource;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void addMsgReport(MsgReport report) {
        report.addGroup(resource, messages);
    }

}
