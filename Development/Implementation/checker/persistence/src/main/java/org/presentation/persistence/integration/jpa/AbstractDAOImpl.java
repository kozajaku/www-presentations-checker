package org.presentation.persistence.integration.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Abstract class which serves as superclass for every DAO JPA implemented
 * class.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public abstract class AbstractDAOImpl {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * <p>
     * Getter for the field <code>entityManager</code>.</p>
     *
     * @return a {@link javax.persistence.EntityManager} object.
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
