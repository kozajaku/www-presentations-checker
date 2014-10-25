package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Domain;

/**
 * <p>DomainDAO interface.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface DomainDAO {

    /**
     * <p>create.</p>
     *
     * @param domain a {@link org.presentation.persistence.model.Domain} object.
     */
    void create(Domain domain);

    /**
     * <p>findAllCheckDomains.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    List<Domain> findAllCheckDomains(Integer checkupId);
}
