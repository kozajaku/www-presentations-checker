package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Checkup;

/**
 *
 * @author radio.koza
 */
public interface CheckupDAO {

    void create(Checkup checkup);

    void update(Checkup checkup);

    Checkup find(Integer checkupId);

    List<Checkup> findAllUserChecks(Integer userId);
}