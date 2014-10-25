package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.LoginDAO;
import org.presentation.persistence.model.Login;

/**
 * Implementation of LoginDAO interface which uses specification of JPA to
 * persist data into database.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class LoginDAOImpl extends AbstractDAOImpl implements LoginDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Login login) {
        login.setIdLogin(null);
        getEntityManager().persist(login);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Login find(Integer loginId) {
        return getEntityManager().find(Login.class, loginId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Login> findAllUserLogins(String email) {
        TypedQuery<Login> q = getEntityManager().createNamedQuery("Login.findByUserEmail", Login.class);
        q.setParameter("email", email);
        return q.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Login findLastUserLogin(String email) {
        TypedQuery<Login> q = getEntityManager().createNamedQuery("Login.findByUserEmail", Login.class);
        q.setParameter("email", email);
        q.setMaxResults(1);
        List<Login> res = q.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

}
