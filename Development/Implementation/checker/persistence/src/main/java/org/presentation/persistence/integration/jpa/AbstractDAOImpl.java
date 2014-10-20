package org.presentation.persistence.integration.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>Abstract AbstractDAOImpl class.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
public abstract class AbstractDAOImpl {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * <p>Getter for the field <code>entityManager</code>.</p>
     *
     * @return a {@link javax.persistence.EntityManager} object.
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
