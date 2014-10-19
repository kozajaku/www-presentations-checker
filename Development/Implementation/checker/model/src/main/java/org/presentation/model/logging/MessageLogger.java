package org.presentation.model.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents message logger, which gathers messages from the
 * resource.
 *
 * @author Jindřich Máca
 */
public class MessageLogger {

    /**
     * Array list of messages from the resource.
     */
    private final List<Message> messages = new ArrayList<>();
    /**
     * Name of the resource.
     */
    private final String resource;

    /**
     * Construct message logger whith specified resource.
     *
     * @param resource Name of the resource.
     */
    public MessageLogger(String resource) {
        this.resource = resource;
    }

    /**
     * Add message to this logger.
     *
     * @param message Message to this logger.
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Add messages list to given message report under resource name.
     *
     * @param report Message report.
     */
    public void pushToMsgReport(MsgReport report) {
        if (messages.isEmpty()) {
            return;
        }
        report.addGroup(resource, messages);
    }

}
