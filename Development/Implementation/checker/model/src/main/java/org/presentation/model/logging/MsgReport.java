package org.presentation.model.logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents generated message report of the included message
 * loggers.
 *
 * @author Jindřich Máca
 */
public class MsgReport {

    /**
     * Message groups genereted by specific message loggers and indetified by
     * their names.
     */
    private final Map<String, List<Message>> msgGroups = new HashMap<>();

    /**
     * Returns all message groups genereted by message loggers.
     *
     * @return All message groups.
     */
    public Map<String, List<Message>> getMsgGroups() {
        return msgGroups;
    }

    /**
     * Add message group to report.
     *
     * @param resource Name of the message logger.
     * @param messages List of messages from message logger.
     */
    public void addGroup(String resource, List<Message> messages) {
        this.msgGroups.put(resource, messages);
    }
}
