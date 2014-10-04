package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.GraphDAO;
import org.presentation.persistence.model.Graph;

/**
 *
 * @author radio.koza
 */
@Dependent
public class GraphDAOImpl extends AbstractDAOImpl implements GraphDAO {

    @Override
    public void create(Graph graph) {
        graph.setIdGraph(null);
        getEntityManager().persist(graph);
    }

    @Override
    public List<Graph> findAllCheckGraphs(Integer checkupId) {
        TypedQuery<Graph> q = getEntityManager().createNamedQuery("Graph.findByCheckupId", Graph.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

}
