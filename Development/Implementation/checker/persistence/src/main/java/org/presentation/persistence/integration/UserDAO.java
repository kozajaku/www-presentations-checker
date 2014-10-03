package org.presentation.persistence.integration;

import org.presentation.persistence.model.User;

/**
 *
 * @author radio.koza
 */
public interface UserDAO {

    void create(User user);

    void update(User user);

    void delete(String email);

    User find(String email);
}
