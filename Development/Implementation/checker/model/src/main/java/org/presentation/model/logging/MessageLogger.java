package org.presentation.model.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents logger for {@link Message}, which gathers messages from
 * the resource. Every control, which wants to produce messages for user output,
 * must first register its own {@link MessageLogger} to
 * {@link MessageLoggerContainer} under resource name specific for this control.
 * In this {@link MessageLogger} are then saved all its messages.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class MessageLogger {

    //Array list of messages from the resource
    private final List<Message> messages = new ArrayList<>();
    //Name of the resource
    private final String resource;

    /**
     * Creates new instance of {@link MessageLogger} with specific resource
     * name.
     *
     * @param resource {@link String} name of the resource
     */
    public MessageLogger(String resource) {
        this.resource = resource;
    }

    /**
     * Add {@link Message} to this {@link MessageLogger}.
     *
     * @param message any known type of {@link Message}
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Push list of {@link Message}, if there is any, from this
     * {@link MessageLogger} to given {@link MsgReport} under its resource name.
     *
     * @param report {@link MsgReport} to which will be list of {@link Message}
     * pushed
     */
    public void pushToMsgReport(MsgReport report) {
        if (messages.isEmpty()) {
            return;
        }
        report.addGroup(resource, messages);
    }

}
