package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.MessageEntityDAO;
import org.presentation.persistence.model.MessageEntity;

/**
 *
 * @author radio.koza
 */
@Dependent
public class MessageEntityDAOImpl extends AbstractDAOImpl implements MessageEntityDAO {

    @Override
    public void create(MessageEntity message) {
        message.setId(null);
        getEntityManager().persist(message);
    }

    @Override
    public List<MessageEntity> findAllCheckMessages(Integer checkupId) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findByCheckupId", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

    @Override
    public List<String> findAllCheckMessageResources(Integer checkupId) {
        TypedQuery<String> q = getEntityManager().createNamedQuery("MessageEntity.findAllResourcesInCheckup", String.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

    @Override
    public List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByResource", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resource", resource);
        return q.getResultList();
    }

}
