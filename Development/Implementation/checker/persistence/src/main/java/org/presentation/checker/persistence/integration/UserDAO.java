package org.presentation.checker.persistence.integration;

import org.presentation.checker.persistence.model.User;

/**
 *
 * @author radio.koza
 */

public interface UserDAO {

    void create(User user);

    void update(User user);

    void delete(Integer userId);

    User find(Integer userId);

    User findByEmail(String email);
}
