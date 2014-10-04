package org.presentation.model.logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jindřich Máca
 */
public class MsgReport {

    private final Map<String, List<Message>> msgGroups = new HashMap<>();

    public Map<String, List<Message>> getMsgGroups() {
        return msgGroups;
    }

    public void addGroup(String resource, List<Message> messages) {
        this.msgGroups.put(resource, messages);
    }
}
