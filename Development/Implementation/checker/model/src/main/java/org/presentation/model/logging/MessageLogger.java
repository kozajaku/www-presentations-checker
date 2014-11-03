package org.presentation.model.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents logger for
 * {@link org.presentation.model.logging.Message}, which gathers messages from
 * the resource. Every control, which wants to produce messages for user output,
 * must first register its own
 * {@link org.presentation.model.logging.MessageLogger} to
 * {@link org.presentation.model.logging.MessageLoggerContainer} under resource
 * name specific for this control. In this
 * {@link org.presentation.model.logging.MessageLogger} are then saved all its
 * messages.
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
     * Creates new instance of
     * {@link org.presentation.model.logging.MessageLogger} with specific
     * resource name.
     *
     * @param resource {@link java.lang.String} name of the resource
     */
    public MessageLogger(String resource) {
        this.resource = resource;
    }

    /**
     * Add {@link org.presentation.model.logging.Message} to this
     * {@link org.presentation.model.logging.MessageLogger}.
     *
     * @param message any known type of
     * {@link org.presentation.model.logging.Message}
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Push list of {@link org.presentation.model.logging.Message}, if there is
     * any, from this {@link org.presentation.model.logging.MessageLogger} to
     * given {@link org.presentation.model.logging.MsgReport} under its resource
     * name.
     *
     * @param report {@link org.presentation.model.logging.MsgReport} to which
     * will be list of {@link org.presentation.model.logging.Message} pushed
     */
    public void pushToMsgReport(MsgReport report) {
        if (messages.isEmpty()) {
            return;
        }
        report.addGroup(resource, messages);
    }

}
