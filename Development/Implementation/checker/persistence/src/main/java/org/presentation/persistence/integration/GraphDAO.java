package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Graph;

/**
 *
 * @author radio.koza
 */
public interface GraphDAO {

    void create(Graph graph);

    List<Graph> findAllCheckGraphs(Integer checkupId);
    
    List<String> listGraphTypes(Integer checkupId);
    
    Graph findGraphByGraphType(Integer checkupId, String graphTypeId);

}
