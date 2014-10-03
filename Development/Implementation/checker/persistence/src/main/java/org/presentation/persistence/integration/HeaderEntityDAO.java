package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.HeaderEntity;

/**
 *
 * @author radio.koza
 */
public interface HeaderEntityDAO {
    
    void create(HeaderEntity headerEntity);
    
    List<HeaderEntity> findAllCheckHeaders(Integer checkupId);
    
}
