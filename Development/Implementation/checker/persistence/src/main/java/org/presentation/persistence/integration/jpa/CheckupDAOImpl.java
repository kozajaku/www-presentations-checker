package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.CheckupDAO;
import org.presentation.persistence.model.Checkup;

/**
 *
 * @author radio.koza
 */
@Dependent
public class CheckupDAOImpl extends AbstractDAOImpl implements CheckupDAO {

    @Override
    public void create(Checkup checkup) {
        checkup.setIdCheckup(null);
        getEntityManager().persist(checkup);
    }

    @Override
    public void update(Checkup checkup) {
        getEntityManager().merge(checkup);
    }

    @Override
    public Checkup find(Integer checkupId) {
        return getEntityManager().find(Checkup.class, checkupId);
    }

    @Override
    public List<Checkup> findAllUserChecks(String email) {
        TypedQuery<Checkup> q = getEntityManager().createNamedQuery("Checkup.findByUserEmail", Checkup.class);
        q.setParameter("email", email);
        return q.getResultList();
    }

}
