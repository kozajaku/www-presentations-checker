package org.presentation.persistence.business;

import java.util.List;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.Login;
import org.presentation.persistence.model.User;

/**
 *
 * @author radio.koza
 */
public interface PersistenceFacade {

    boolean createNewUser(String email, String pass, String name, String surname);

    void createNewCheckup(Checkup checkup);

    void addUserLogin(User user, String address);

    void editUser(User user);

    User findUser(Integer userId);

    User findUser(String email);

    List<Checkup> findUserCheckings(User user);

    Checkup findCheckup(Integer checkId);

    void updateCheckup(Checkup checkup);

    List<Login> findUserLogins(User user);

    Login findLastUserLogin(User user);

}
