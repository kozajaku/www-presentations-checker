package org.presentation.persistence.integration;

import org.presentation.persistence.model.User;

/**
 * UserDAO interface that serves as layer between business persistence facade
 * and persistence context.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface UserDAO {

    /**
     * Method creates new user. Note that this method fails if user with
     * specified email already exists in database.
     *
     * @param user Instance of {@link org.presentation.persistence.model.User}
     * to be persisted in database
     */
    void create(User user);

    /**
     * Updates information about {@link org.presentation.persistence.model.User}
     * in database.
     *
     * @param user Instance of {@link org.presentation.persistence.model.User}
     * that should be updated
     */
    void update(User user);

    /**
     * Method deletes user in database specified by user's email address.
     *
     * @param email Email address of user that should be deleted from database.
     */
    void delete(String email);

    /**
     * Method finds instance of {@link org.presentation.persistence.model.User}
     * in database by user's email address.
     *
     * @param email Email address o wanted user
     * @return Instance of {@link org.presentation.persistence.model.User} with
     * specified email address; <code>null</code> if there is no user with such
     * email address
     */
    User find(String email);
}
