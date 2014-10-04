package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.LoginDAO;
import org.presentation.persistence.model.Login;

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
    public List<Login> findAllUserLogins(String email) {
        TypedQuery<Login> q = getEntityManager().createNamedQuery("Login.findByUserEmail", Login.class);
        q.setParameter("email", email);
        return q.getResultList();
    }

    @Override
    public Login findLastUserLogin(String email) {
        TypedQuery<Login> q = getEntityManager().createNamedQuery("Login.findByUserEmail", Login.class);
        q.setParameter("email", email);
        q.setMaxResults(1);
        List<Login> res = q.getResultList();
        return res.isEmpty() ? null : res.get(0);
    }

}
