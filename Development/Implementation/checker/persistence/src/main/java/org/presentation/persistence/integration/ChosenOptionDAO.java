package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.ChosenOption;

/**
 *
 * @author radio.koza
 */
public interface ChosenOptionDAO {
    
    void create(ChosenOption option);
    
    List<ChosenOption> findAllCheckOptions(Integer checkupId);
    
}
