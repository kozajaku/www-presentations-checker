package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.MessageEntity;

/**
 *
 * @author radio.koza
 */
public interface MessageEntityDAO {

    void create(MessageEntity message);

    List<MessageEntity> findAllCheckMessages(Integer checkupId);

    List<MessageEntity> findAllCheckMessages(Integer checkupId, int offset, int count);

    List<String> findAllCheckMessageResources(Integer checkupId);

    List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource);

    List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource, int offset, int count);
    
    List<MessageEntity> findAllCheckMessagesFromResources(Integer checkupId, List<String> resources);

    List<MessageEntity> findAllCheckMessagesFromResources(Integer checkupId, List<String> resources, int offset, int count);
    
    int countCheckMessages(Integer checkupId);
    
    int countCheckMessagesFromResources(Integer checkupId, List<String> resources);
}
