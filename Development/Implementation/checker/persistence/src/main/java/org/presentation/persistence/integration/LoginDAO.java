package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.Login;

/**
 *
 * @author radio.koza
 */
public interface LoginDAO {

    void create(Login login);

    Login find(Integer loginId);

    List<Login> findAllUserLogins(String email);

    Login findLastUserLogin(String email);
}
