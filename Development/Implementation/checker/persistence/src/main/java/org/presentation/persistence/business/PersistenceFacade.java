package org.presentation.persistence.business;

import java.util.List;
import org.presentation.model.Domain;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.Login;
import org.presentation.persistence.model.User;
import org.presentation.model.Header;
import org.presentation.model.logging.Message;
import org.presentation.persistence.model.ChosenOption;
import org.presentation.persistence.model.Graph;

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

    List<Checkup> findUserCheckings(User user, int offset, int count);

    Checkup findCheckup(Integer checkId);

    Checkup findCheckupInitializedInputs(Integer checkId);

    List<Checkup> findNotEndedCheckupsStateOrdered();

    Checkup fetchNewlyCreatedCheckup();

    int countUserCheckups(User user);

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
    void addGraphsToCheckup(Checkup checkup, List<Graph> graphs);

    List<Graph> findCheckupGraphs(Checkup checkup);

    List<String> listGraphTypes(Checkup checkup);

    Graph findGraphByGraphType(Checkup checkup, String graphType);

    //message
    void addMessagesToCheckup(Checkup checkup, List<Message> messages, String resource);

    List<Message> findCheckupMessages(Checkup checkup);

    List<Message> findCheckupMessages(Checkup checkup, int offset, int count);

    List<String> findCheckupMessageResources(Checkup checkup);

    List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource);

    List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource, int offset, int count);

    List<Message> findCheckupMessagesWithResources(Checkup checkup, List<String> resources);

    List<Message> findCheckupMessagesWithResources(Checkup checkup, List<String> resources, int offset, int count);

    List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators);

    List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators, int offset, int count);

    List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators);

    List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators, int offset, int count);

    void flush();

    int countCheckupMessages(Checkup checkup);

    int countCheckupMessagesWithResources(Checkup checkup, List<String> resources);

    int countCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators);

    int countCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators);
}
