package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Login;

/**
 * <p>LoginDAO interface.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
public interface LoginDAO {

    /**
     * <p>create.</p>
     *
     * @param login a {@link org.presentation.persistence.model.Login} object.
     */
    void create(Login login);

    /**
     * <p>find.</p>
     *
     * @param loginId a {@link java.lang.Integer} object.
     * @return a {@link org.presentation.persistence.model.Login} object.
     */
    Login find(Integer loginId);

    /**
     * <p>findAllUserLogins.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    List<Login> findAllUserLogins(String email);

    /**
     * <p>findLastUserLogin.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link org.presentation.persistence.model.Login} object.
     */
    Login findLastUserLogin(String email);
}
