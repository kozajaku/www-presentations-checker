package org.presentation.persistence.integration;

import org.presentation.persistence.model.User;

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
