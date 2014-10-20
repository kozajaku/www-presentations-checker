package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.HeaderEntityDAO;
import org.presentation.persistence.model.HeaderEntity;

/**
 * <p>HeaderEntityDAOImpl class.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@Dependent
public class HeaderEntityDAOImpl extends AbstractDAOImpl implements HeaderEntityDAO {

    /** {@inheritDoc} */
    @Override
    public void create(HeaderEntity headerEntity) {
        headerEntity.setIdHeader(null);
        getEntityManager().persist(headerEntity);
    }

    /** {@inheritDoc} */
    @Override
    public List<HeaderEntity> findAllCheckHeaders(Integer checkupId) {
        TypedQuery<HeaderEntity> q = getEntityManager().createNamedQuery("HeaderEntity.findByCheckupId", HeaderEntity.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

}
