package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Graph;

/**
 * <p>GraphDAO interface.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
public interface GraphDAO {

    /**
     * <p>create.</p>
     *
     * @param graph a {@link org.presentation.persistence.model.Graph} object.
     */
    void create(Graph graph);

    /**
     * <p>findAllCheckGraphs.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    List<Graph> findAllCheckGraphs(Integer checkupId);

    /**
     * <p>listGraphTypes.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    List<String> listGraphTypes(Integer checkupId);

    /**
     * <p>findGraphByGraphType.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param graphTypeId a {@link java.lang.String} object.
     * @return a {@link org.presentation.persistence.model.Graph} object.
     */
    Graph findGraphByGraphType(Integer checkupId, String graphTypeId);

}
