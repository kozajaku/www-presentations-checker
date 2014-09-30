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
        user.setIdUser(null);
        getEntityManager().persist(user);
    }

    @Override
    public void update(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public void delete(Integer userId) {
        getEntityManager().remove(getEntityManager().getReference(User.class, userId));
    }

    @Override
    public User find(Integer userId) {
        return getEntityManager().find(User.class, userId);
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> q = getEntityManager().createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        List<User> res = q.getResultList();
        return res.isEmpty() ? null : res.get(0);//unique constraint must be set in order to work this
    }

}
