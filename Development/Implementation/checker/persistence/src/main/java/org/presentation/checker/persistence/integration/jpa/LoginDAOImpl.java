package org.presentation.checker.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.checker.persistence.integration.LoginDAO;
import org.presentation.checker.persistence.model.Login;

/**
 *
 * @author radio.koza
 */
@Dependent
public class LoginDAOImpl extends AbstractDAOImpl implements LoginDAO {

    @Override
    public void create(Login login) {
        login.setIdLogin(null);
        getEntityManager().persist(login);
    }

    @Override
    public Login find(Integer loginId) {
        return getEntityManager().find(Login.class, loginId);
    }

    @Override
    public List<Login> findAllUserLogins(Integer userId) {
        TypedQuery<Login> q = getEntityManager().createNamedQuery("Login.findByUserId", Login.class);
        q.setParameter("userId", userId);
        return q.getResultList();
    }

    @Override
    public Login findLastUserLogin(Integer userId) {
        TypedQuery<Login> q = getEntityManager().createNamedQuery("Login.findByUserId", Login.class);
        q.setParameter("userId", userId);
        q.setMaxResults(1);
        List<Login> res = q.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

}
