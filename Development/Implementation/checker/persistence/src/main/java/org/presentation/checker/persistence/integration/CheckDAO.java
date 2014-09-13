package org.presentation.checker.persistence.integration;

import java.util.List;
import org.hibernate.annotations.Check;
import org.presentation.checker.persistence.model.Checkup;

/**
 *
 * @author radio.koza
 */
public interface CheckDAO {

    void create(Checkup checkup);

    void update(Checkup checkup);

    Check find(Integer checkupId);

    List<Checkup> findAllUserChecks(Integer userId);
}
