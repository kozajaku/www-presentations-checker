package org.presentation.persistence.business;

import java.util.List;
import org.presentation.model.Domain;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.Login;
import org.presentation.persistence.model.User;
import org.presentation.model.Header;
import org.presentation.model.logging.Message;
import org.presentation.persistence.model.ChosenOption;

/**
 *
 * @author radio.koza
 */
public interface PersistenceFacade {

    //user methods
    boolean createNewUser(String email, String pass, String name, String surname);

    User findUser(String email);

    void editUser(User user);

    void addUserLogin(User user, String address);

    Login findLastUserLogin(User user);

    List<Login> findUserLogins(User user);
    
    //checkup methods
    void createNewCheckup(Checkup checkup);

    void updateCheckup(Checkup checkup);

    List<Checkup> findUserCheckings(User user);

    Checkup findCheckup(Integer checkId);
    
    Checkup findCheckupInitializedInputs(Integer checkId);

    //headers
    void addHeadersToCheckup(Checkup checkup, List<Header> headers);
    
    List<Header> findCheckupHeaders(Checkup checkup);
    
    //options
    void addOptionsToCheckup(Checkup checkup, List<String> options);
    
    List<ChosenOption> findCheckupOptions(Checkup checkup);
    
    //domain
    void addDomainsToCheckup(Checkup checkup, List<Domain> domains);
    
    List<Domain> findCheckupDomains(Checkup checkup);
    
    //graph
    void addGraphsToDomain(Checkup checkup, List<String> graphSources);
    
    List<String> findCheckupGraphs(Checkup checkup);
    
    //message
    void addMessagesToDomain(Checkup checkup, List<Message> messages, String resource);
    
    List<Message> findCheckupMessages(Checkup checkup);
    
    List<String> findCheckupMessageResources(Checkup checkup);
    
    List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource);
}
