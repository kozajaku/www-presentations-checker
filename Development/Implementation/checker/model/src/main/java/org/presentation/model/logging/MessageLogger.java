package org.presentation.model.logging;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jindřich Máca
 */
public class MessageLogger {

    private final List<Message> messages = new ArrayList<>();
    private final String resource;

    public MessageLogger(String resource) {
        this.resource = resource;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void pushToMsgReport(MsgReport report) {
        report.addGroup(resource, messages);
    }

}
