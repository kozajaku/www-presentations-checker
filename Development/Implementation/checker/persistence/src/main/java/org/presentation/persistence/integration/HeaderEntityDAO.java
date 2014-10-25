package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.HeaderEntity;

/**
 * HeaderEntityDAO interface that serves as layer between business persistence
 * facade and persistence context.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface HeaderEntityDAO {

    /**
     * Method persists new
     * {@link org.presentation.persistence.model.HeaderEntity} object to
     * database. Note that header entity must have checkup assigned or this
     * method will fail.
     *
     * @param headerEntity
     * {@link org.presentation.persistence.model.HeaderEntity} object to be
     * persisted to database
     */
    void create(HeaderEntity headerEntity);

    /**
     * Method finds collection of all
     * {@link org.presentation.persistence.model.HeaderEntity} assigned to
     * checkup with specified primary key.
     *
     * @param checkupId Primary key of the checkup
     * @return List of all
     * {@link org.presentation.persistence.model.HeaderEntity} instances
     * assigned to checkup
     */
    List<HeaderEntity> findAllCheckHeaders(Integer checkupId);

}
