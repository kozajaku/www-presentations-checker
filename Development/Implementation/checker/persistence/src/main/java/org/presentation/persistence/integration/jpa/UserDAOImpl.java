package org.presentation.persistence.integration.jpa;

import javax.enterprise.context.Dependent;
import org.presentation.persistence.integration.UserDAO;
import org.presentation.persistence.model.User;

/**
 * Implementation of UserDAO interface which uses specification of JPA to
 * persist data into database.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class UserDAOImpl extends AbstractDAOImpl implements UserDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(User user) {
        getEntityManager().persist(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(User user) {
        getEntityManager().merge(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String email) {
        getEntityManager().remove(getEntityManager().getReference(User.class, email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User find(String email) {
        return getEntityManager().find(User.class, email);
    }

}
