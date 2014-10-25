package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.HeaderEntity;

/**
 * <p>HeaderEntityDAO interface.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface HeaderEntityDAO {

    /**
     * <p>create.</p>
     *
     * @param headerEntity a {@link org.presentation.persistence.model.HeaderEntity} object.
     */
    void create(HeaderEntity headerEntity);

    /**
     * <p>findAllCheckHeaders.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    List<HeaderEntity> findAllCheckHeaders(Integer checkupId);

}
