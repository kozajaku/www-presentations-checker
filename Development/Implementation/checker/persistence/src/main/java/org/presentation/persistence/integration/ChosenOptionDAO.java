package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.ChosenOption;

/**
 * <p>ChosenOptionDAO interface.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface ChosenOptionDAO {

    /**
     * <p>addOptionToCheckup.</p>
     *
     * @param option a {@link org.presentation.persistence.model.ChosenOption} object.
     * @param checkupId a {@link java.lang.Integer} object.
     */
    void addOptionToCheckup(ChosenOption option, Integer checkupId);

    /**
     * <p>findAllCheckOptions.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    List<ChosenOption> findAllCheckOptions(Integer checkupId);

}
