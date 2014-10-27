package org.presentation.model.logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents generated {@link MsgReport} from all added
 * {@link MessageLogger} given by resource name of control, they are related to
 * and {@link List} of {@link Message}, that this control produced.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class MsgReport {

    //Message groups genereted by specific message loggers and indetified by their names
    private final Map<String, List<Message>> msgGroups = new HashMap<>();

    /**
     * Returns all message groups genereted by all added {@link MessageLogger}
     * and represented by their resource names and {@link List}s of
     * {@link Message}.
     *
     * @return all message groups genereted by all added {@link MessageLogger}
     */
    public Map<String, List<Message>> getMsgGroups() {
        return msgGroups;
    }

    /**
     * Add message group for specific {@link MessageLogger}, represented by its
     * resource name and its {@link List} of {@link Message}.
     *
     * @param resource {@link String} resource name of the {@link MessageLogger}
     * @param messages {@link List} of {@link Message} from the
     * {@link MessageLogger}
     */
    public void addGroup(String resource, List<Message> messages) {
        this.msgGroups.put(resource, messages);
    }
}
