package org.presentation.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Jindřich Máca
 */
public class MsgReport {

    private Map<String, List<Message>> msgGroups;

    public Map<String, List<Message>> getMsgGroups() {
        return msgGroups;
    }

    public void addGroup(String resource, List<Message> messages) {
        this.msgGroups.put(resource, messages);
    }
}
