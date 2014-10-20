package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.DomainDAO;
import org.presentation.persistence.model.Domain;

/**
 * <p>DomainDAOImpl class.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@Dependent
public class DomainDAOImpl extends AbstractDAOImpl implements DomainDAO {

    /** {@inheritDoc} */
    @Override
    public void create(Domain domain) {
        domain.setIdDomain(null);
        getEntityManager().persist(domain);
    }

    /** {@inheritDoc} */
    @Override
    public List<Domain> findAllCheckDomains(Integer checkupId) {
        TypedQuery<Domain> q = getEntityManager().createNamedQuery("Domain.findByCheckupId", Domain.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

}
