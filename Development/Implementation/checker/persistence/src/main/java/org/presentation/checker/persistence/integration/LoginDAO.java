package org.presentation.checker.persistence.integration;

import java.util.List;
import org.presentation.checker.persistence.model.Login;

/**
 *
 * @author radio.koza
 */
public interface LoginDAO {

    void create(Login login);

    Login find(Integer loginId);

    List<Login> findAllUserLogins(Integer userId);

    Login findLastUserLogin(Integer userId);
}
