package org.presentation.model.logging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents generated
 * {@link org.presentation.model.logging.MsgReport} from all added
 * {@link org.presentation.model.logging.MessageLogger} given by resource name
 * of control, they are related to and {@link java.util.List} of
 * {@link org.presentation.model.logging.Message}, that this control produced.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class MsgReport {

    //Message groups genereted by specific message loggers and indetified by their names
    private final Map<String, List<Message>> msgGroups = new HashMap<>();

    /**
     * Returns all message groups genereted by all added
     * {@link org.presentation.model.logging.MessageLogger} and represented by
     * their resource names and {@link java.util.List}s of
     * {@link org.presentation.model.logging.Message}.
     *
     * @return all message groups genereted by all added
     * {@link org.presentation.model.logging.MessageLogger}
     */
    public Map<String, List<Message>> getMsgGroups() {
        return msgGroups;
    }

    /**
     * Add message group for specific
     * {@link org.presentation.model.logging.MessageLogger}, represented by its
     * resource name and its {@link java.util.List} of
     * {@link org.presentation.model.logging.Message}.
     *
     * @param resource {@link java.lang.String} resource name of the
     * {@link org.presentation.model.logging.MessageLogger}
     * @param messages {@link java.util.List} of
     * {@link org.presentation.model.logging.Message} from the
     * {@link org.presentation.model.logging.MessageLogger}
     */
    public void addGroup(String resource, List<Message> messages) {
        this.msgGroups.put(resource, messages);
    }
}
