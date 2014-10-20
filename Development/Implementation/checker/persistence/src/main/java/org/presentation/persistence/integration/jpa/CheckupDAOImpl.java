package org.presentation.persistence.integration.jpa;

import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.CheckupDAO;
import org.presentation.persistence.model.CheckState;
import org.presentation.persistence.model.Checkup;

/**
 * <p>CheckupDAOImpl class.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@Dependent
public class CheckupDAOImpl extends AbstractDAOImpl implements CheckupDAO {

    /** {@inheritDoc} */
    @Override
    public void create(Checkup checkup) {
        checkup.setIdCheckup(null);
        getEntityManager().persist(checkup);
    }

    /** {@inheritDoc} */
    @Override
    public void update(Checkup checkup) {
        getEntityManager().merge(checkup);
    }

    /** {@inheritDoc} */
    @Override
    public Checkup find(Integer checkupId) {
        return getEntityManager().find(Checkup.class, checkupId);
    }

    /** {@inheritDoc} */
    @Override
    public List<Checkup> findAllUserChecks(String email) {
        TypedQuery<Checkup> q = getEntityManager().createNamedQuery("Checkup.findByUserEmail", Checkup.class);
        q.setParameter("email", email);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public void flush() {
        getEntityManager().flush();
    }

    /** {@inheritDoc} */
    @Override
    public List<Checkup> findAllWithState(CheckState[] states) {
        TypedQuery<Checkup> q = getEntityManager().createQuery("SELECT c FROM Checkup c WHERE c.state IN :states ORDER BY c.checkingCreated", Checkup.class);
        q.setParameter("states", Arrays.asList(states));
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Checkup> findAllUserChecks(String email, int offset, int count) {
        if (offset < 0 || count <= 0) {
            throw new IllegalArgumentException("offset negative or count not positive");
        }
        TypedQuery<Checkup> q = getEntityManager().createNamedQuery("Checkup.findByUserEmail", Checkup.class);
        q.setParameter("email", email);
        q.setFirstResult(offset);
        q.setMaxResults(count);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public int countUserChecks(String email) {
        TypedQuery<Integer> q = getEntityManager().createNamedQuery("Checkup.countUserCheckups", Integer.class);
        q.setParameter("email", email);
        return q.getSingleResult();
    }
}
