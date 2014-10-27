package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Login;

/**
 * LoginDAO interface that serves as layer between business persistence facade
 * and persistence context.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface LoginDAO {

    /**
     * Method persists new {@link org.presentation.persistence.model.Login} to
     * database. Note that login must have user assigned or or this method will
     * fail.
     *
     * @param login {@link org.presentation.persistence.model.Login} to be
     * persisted to database
     */
    void create(Login login);

    /**
     * Method finds {@link org.presentation.persistence.model.Login} with
     * specified primary key.
     *
     * @param loginId Primary key of wanted
     * {@link org.presentation.persistence.model.Login} object
     * @return {@link org.presentation.persistence.model.Login} with specified
     * primary key; <code>null</code> if not found in database
     */
    Login find(Integer loginId);

    /**
     * Method finds collection of
     * {@link org.presentation.persistence.model.Login} assigned to user
     * specified by email address.
     *
     * @param email Email address identifying user
     * @return List of {@link org.presentation.persistence.model.Login} assigned
     * to the user; collection could be empty but never <code>null</code>
     */
    List<Login> findAllUserLogins(String email);

    /**
     * Method finds last {@link org.presentation.persistence.model.Login} of the
     * user specified by email address.
     *
     * @param email Email address identifying user
     * @return Last {@link org.presentation.persistence.model.Login} of the
     * user; <code>null</code> if it is his first login
     */
    Login findLastUserLogin(String email);
}
