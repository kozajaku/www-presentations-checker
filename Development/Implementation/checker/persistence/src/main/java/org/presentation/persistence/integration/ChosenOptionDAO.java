package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.ChosenOption;

/**
 * ChosenOptionDAO interface that serves as layer between business persistence
 * facade and persistence context.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface ChosenOptionDAO {

    /**
     * Method persists new
     * {@link org.presentation.persistence.model.ChosenOption} object to
     * database if it is not already present and it assigs option to checkup
     * with specified primary key.
     *
     * @param option Option to be persisted in database and assigned to checkup
     * @param checkupId Primary key of the checkup that should be connected with
     * new option
     */
    void addOptionToCheckup(ChosenOption option, Integer checkupId);

    /**
     * Method finds collection of
     * {@link org.presentation.persistence.model.ChosenOption} objects assigned
     * to checkup with specified primary key.
     *
     * @param checkupId Primary key of the checkup
     * @return List of {@link org.presentation.persistence.model.ChosenOption}
     * instances connected to the checkup with specified primary key
     */
    List<ChosenOption> findAllCheckOptions(Integer checkupId);

}
