package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Graph;

/**
 * GraphDAO interface that serves as layer between business persistence facade
 * and persistence context.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface GraphDAO {

    /**
     * Method persists new {@link org.presentation.persistence.model.Graph}
     * object to database. Note that graph instance must have checkup assigned
     * or this method will fail.
     *
     * @param graph Instance of {@link org.presentation.persistence.model.Graph}
     * to be persisted in database
     */
    void create(Graph graph);

    /**
     * Method returns collection of
     * {@link org.presentation.persistence.model.Graph} objects assigned to
     * checkup with specified primary key.
     *
     * @param checkupId Primary key of the checkup
     * @return List of {@link org.presentation.persistence.model.Graph}
     * instances assigned to the checkup
     */
    List<Graph> findAllCheckGraphs(Integer checkupId);

    /**
     * Method returns collection of distinct graph type names assigned to
     * checkup with specified primary key.
     *
     * @param checkupId Primary key of the checkup
     * @return List of distinct graph type names
     */
    List<String> listGraphTypes(Integer checkupId);

    /**
     * Method returns {@link org.presentation.persistence.model.Graph} with
     * specified graph type assigned to checkup with specified primary key.
     *
     * @param checkupId Primary key of the checkup
     * @param graphTypeId Name of the graph type
     * @return {@link org.presentation.persistence.model.Graph} matching the
     * filter; <code>null</code> if no such graph is persisted in database
     */
    Graph findGraphByGraphType(Integer checkupId, String graphTypeId);

}
