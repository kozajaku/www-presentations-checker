package org.presentation.checker.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.checker.persistence.integration.DomainDAO;
import org.presentation.checker.persistence.model.Domain;

/**
 *
 * @author radio.koza
 */
@Dependent
public class DomainDAOImpl extends AbstractDAOImpl implements DomainDAO{

    @Override
    public void create(Domain domain) {
        domain.setIdDomain(null);
        getEntityManager().persist(domain);
    }

    @Override
    public List<Domain> findAllCheckDomains(Integer checkupId) {
        TypedQuery<Domain> q = getEntityManager().createNamedQuery("Domain.findByCheckupId", Domain.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }
    
}
