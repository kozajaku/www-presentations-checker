package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.UserDAO;
import org.presentation.persistence.model.User;

/**
 *
 * @author radio.koza
 */
@Dependent
public class UserDAOImpl extends AbstractDAOImpl implements UserDAO {

    @Override
    public void create(User user) {
        getEntityManager().persist(user);
    }

    @Override
    public void update(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public void delete(String email) {
        getEntityManager().remove(getEntityManager().getReference(User.class, email));
    }

    @Override
    public User find(String email) {
        return getEntityManager().find(User.class, email);
    }

}
