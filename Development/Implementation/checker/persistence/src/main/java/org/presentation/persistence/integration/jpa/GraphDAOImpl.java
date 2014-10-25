package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.GraphDAO;
import org.presentation.persistence.model.Graph;

/**
 * <p>GraphDAOImpl class.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class GraphDAOImpl extends AbstractDAOImpl implements GraphDAO {

    /** {@inheritDoc} */
    @Override
    public void create(Graph graph) {
        graph.setIdGraph(null);
        getEntityManager().persist(graph);
    }

    /** {@inheritDoc} */
    @Override
    public List<Graph> findAllCheckGraphs(Integer checkupId) {
        TypedQuery<Graph> q = getEntityManager().createNamedQuery("Graph.findByCheckupId", Graph.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<String> listGraphTypes(Integer checkupId) {
        TypedQuery<String> q = getEntityManager().createNamedQuery("Graph.listGraphTypes", String.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public Graph findGraphByGraphType(Integer checkupId, String graphTypeId) {
        TypedQuery<Graph> q = getEntityManager().createNamedQuery("Graph.findGraphByGraphType", Graph.class);
        q.setParameter("checkupId", checkupId);
        q.setParameter("graphType", graphTypeId);
        List<Graph> results = q.getResultList();
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

}
