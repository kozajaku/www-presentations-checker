package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.MessageEntityDAO;
import org.presentation.persistence.model.MessageEntity;

/**
 * Implementation of MessageEntityDAO interface which uses specification of JPA
 * to persist data into database.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class MessageEntityDAOImpl extends AbstractDAOImpl implements MessageEntityDAO {

    /** {@inheritDoc} */
    @Override
    public void create(MessageEntity message) {
        message.setId(null);
        getEntityManager().persist(message);
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessages(Integer checkupId) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findByCheckupId", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<String> findAllCheckMessageResources(Integer checkupId) {
        TypedQuery<String> q = getEntityManager().createNamedQuery("MessageEntity.findAllResourcesInCheckup", String.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByResource", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resource", resource);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessages(Integer checkupId, int offset, int count) {
        if (offset < 0 || count <= 0) {
            throw new IllegalArgumentException("offset negative or count not positive");
        }
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findByCheckupId", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setFirstResult(offset);
        q.setMaxResults(count);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource, int offset, int count) {
        if (offset < 0 || count <= 0) {
            throw new IllegalArgumentException("offset negative or count not positive");
        }
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByResource", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resource", resource);
        q.setFirstResult(offset);
        q.setMaxResults(count);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessagesFromResources(Integer checkupId, List<String> resources) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByResources", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resources", resources);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessagesFromResources(Integer checkupId, List<String> resources, int offset, int count) {
        if (offset < 0 || count <= 0) {
            throw new IllegalArgumentException("offset negative or count not positive");
        }
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByResources", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resources", resources);
        q.setFirstResult(offset);
        q.setMaxResults(count);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public int countCheckMessages(Integer checkupId) {
        TypedQuery<Integer> q = getEntityManager().createNamedQuery("MessageEntity.countCheckupMessages", Integer.class);
        q.setParameter("checkupId", checkupId);
        return q.getSingleResult();
    }

    /** {@inheritDoc} */
    @Override
    public int countCheckMessagesFromResources(Integer checkupId, List<String> resources) {
        TypedQuery<Integer> q = getEntityManager().createNamedQuery("MessageEntity.countCheckupMessagesFromResource", Integer.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resources", resources);
        return q.getSingleResult();
    }

    /** {@inheritDoc} */
    @Override
    public int countCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators) {
        TypedQuery<Integer> q = getEntityManager().createNamedQuery("MessageEntity.countCheckupMessagesByDiscriminators", Integer.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("discriminators", discriminators);
        return q.getSingleResult();
    }

    /** {@inheritDoc} */
    @Override
    public int countCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators) {
        TypedQuery<Integer> q = getEntityManager().createNamedQuery("MessageEntity.countCheckupMessagesByResourcesDiscriminators", Integer.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resources", resources);
        q.setParameter("discriminators", discriminators);
        return q.getSingleResult();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByDiscriminators", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("discriminators", discriminators);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators, int offset, int count) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByDiscriminators", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("discriminators", discriminators);
        q.setFirstResult(offset);
        q.setMaxResults(count);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByResourcesDiscriminators", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resources", resources);
        q.setParameter("discriminators", discriminators);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<MessageEntity> findAllCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators, int offset, int count) {
        TypedQuery<MessageEntity> q = getEntityManager().createNamedQuery("MessageEntity.findAllInCheckupByResourcesDiscriminators", MessageEntity.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("resources", resources);
        q.setParameter("discriminators", discriminators);
        q.setFirstResult(offset);
        q.setMaxResults(count);
        return q.getResultList();
    }

}
