package org.presentation.persistence.integration;

import org.presentation.persistence.model.User;

/**
 * <p>UserDAO interface.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
public interface UserDAO {

    /**
     * <p>create.</p>
     *
     * @param user a {@link org.presentation.persistence.model.User} object.
     */
    void create(User user);

    /**
     * <p>update.</p>
     *
     * @param user a {@link org.presentation.persistence.model.User} object.
     */
    void update(User user);

    /**
     * <p>delete.</p>
     *
     * @param email a {@link java.lang.String} object.
     */
    void delete(String email);

    /**
     * <p>find.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link org.presentation.persistence.model.User} object.
     */
    User find(String email);
}
