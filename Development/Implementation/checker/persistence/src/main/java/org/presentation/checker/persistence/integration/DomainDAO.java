package org.presentation.checker.persistence.integration;

import java.util.List;
import org.presentation.checker.persistence.model.Domain;

/**
 *
 * @author radio.koza
 */
public interface DomainDAO {

    void create(Domain domain);

    List<Domain> findAllCheckDomains(Integer checkupId);
}
