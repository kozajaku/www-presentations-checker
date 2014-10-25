package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Domain;

/**
 * DomainDAO interface that serves as layer between business persistence facade
 * and persistence context.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface DomainDAO {

    /**
     * Method persists new instance of
     * {@link org.presentation.persistence.model.Domain} class to database. Note
     * that passed domain parameter must have the checkup set or method will
     * fail.
     *
     * @param domain New {@link org.presentation.persistence.model.Domain} to be
     * persisted in database
     */
    void create(Domain domain);

    /**
     * Method returns collection of all
     * {@link org.presentation.persistence.model.Domain} objects assigned to
     * checkup with specified primary key.
     *
     * @param checkupId Primary key of the checkup
     * @return List of {@link org.presentation.persistence.model.Domain} objects
     * assigned to the checkup
     */
    List<Domain> findAllCheckDomains(Integer checkupId);
}
