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
    /**
     * Creates new user.
     *
     * @param email User email
     * @param pass User password
     * @param name Name of user
     * @param surname Surname of User
     * @return True if user was created sucesfully.
     */
    boolean createNewUser(String email, String pass, String name, String surname);

    /**
     * Finds user based on email.
     *
     * @param email Email of searched user
     * @return User with matching email or null if there isn't match.
     */
    User findUser(String email);

    /**
     * Edits user with matching email. Updates values.
     *
     * @param user User with new values.
     */
    void editUser(User user);

    /**
     * Adds users login.
     *
     * @param user User that logged in
     * @param address IP address from which is user logged in
     */
    void addUserLogin(User user, String address);

    /**
     * Finds last user login.
     *
     * @param user User to search
     * @return Last user login.
     */
    Login findLastUserLogin(User user);

    /**
     * Finds all user logins
     *
     * @param user User to search
     * @return User login history.
     */
    List<Login> findUserLogins(User user);

    //checkup methods
    /**
     * Creates new check-up.
     *
     * @param checkup New check-up.
     */
    void createNewCheckup(Checkup checkup);

    /**
     * Updates check-up with same id.
     *
     * @param checkup Updated check-up.
     */
    void updateCheckup(Checkup checkup);

    /**
     * Finds user check-ups.
     *
     * @param user User to search
     * @return All user check-ups.
     */
    List<Checkup> findUserCheckings(User user);

    /**
     * Finds user check-ups.
     *
     * @param user User to search
     * @param offset Number of first check-up to return
     * @param count Count of check-ups to return
     * @return Count of user check-ups from offset.
     */
    List<Checkup> findUserCheckings(User user, int offset, int count);

    /**
     * Finds check-up.
     *
     * @param checkId Check-up id
     * @return Check-up with same id or null if no match was found.
     */
    Checkup findCheckup(Integer checkId);

    Checkup findCheckupInitializedInputs(Integer checkId);

    /**
     * Finds unfinished check-ups.
     *
     * @return Unfinished check-ups in order based on state (checking, pending,
     * created).
     */
    List<Checkup> findNotEndedCheckupsStateOrdered();

    /**
     * Fetches a newly created check-up.
     *
     * @return Newly created check-up or null if there isn't any.
     */
    Checkup fetchNewlyCreatedCheckup();

    /**
     * Counts check-ups created by user.
     *
     * @param user Check-up creator
     * @return Check-ups count.
     */
    int countUserCheckups(User user);

    //headers
    /**
     * Adds http/https headers to check-up.
     *
     * @param checkup Target check-up
     * @param headers Http/https headers to add
     */
    void addHeadersToCheckup(Checkup checkup, List<Header> headers);

    /**
     * Finds http/https headers which belongs to check-up.
     *
     * @param checkup Check-up to be searched
     * @return List of http/https headers.
     */
    List<Header> findCheckupHeaders(Checkup checkup);

    //options
    /**
     * Adds options to check-up.
     *
     * @param checkup Check-up without options
     * @param options Check-up options to add
     */
    void addOptionsToCheckup(Checkup checkup, List<String> options);

    /**
     * Finds check-up options.
     *
     * @param checkup Check-up to be searched
     * @return Check-up options.
     */
    List<ChosenOption> findCheckupOptions(Checkup checkup);

    //domain
    /**
     * Adds domains to check-up.
     *
     * @param checkup Check-up without domains
     * @param domains Domains to add
     */
    void addDomainsToCheckup(Checkup checkup, List<Domain> domains);

    /**
     * Finds check-up domains.
     *
     * @param checkup Check-up to be searched
     * @return Check-up domains.
     */
    List<Domain> findCheckupDomains(Checkup checkup);

    //graph
    /**
     * Add graphs to check-up.
     *
     * @param checkup Check-up without graphs.
     * @param graphs Graphs to add.
     */
    void addGraphsToCheckup(Checkup checkup, List<Graph> graphs);

    /**
     * Finds check-up graphs.
     *
     * @param checkup Check-up to be searched
     * @return Check-up graphs.
     */
    List<Graph> findCheckupGraphs(Checkup checkup);

    /**
     * Returns list of names of graph types.
     *
     * @param checkup Check-up to be searched
     * @return List of graph types.
     */
    List<String> listGraphTypes(Checkup checkup);

    /**
     * Finds graph by graph type.
     *
     * @param checkup Check-up to be searched
     * @param graphType Type of graph to be searched
     * @return Graph of matching type from check-up.
     */
    Graph findGraphByGraphType(Checkup checkup, String graphType);

    //message
    /**
     * Adds messages to check-up.
     *
     * @param checkup Check-up to add to
     * @param messages Messages to add
     * @param resource Source of messages
     */
    void addMessagesToCheckup(Checkup checkup, List<Message> messages, String resource);

    /**
     * Finds checkup messages.
     *
     * @param checkup Check-up to be searched
     * @return All messages belongs to the check-up.
     */
    List<Message> findCheckupMessages(Checkup checkup);

    /**
     * Finds checkup messages.
     *
     * @param checkup Check-up to be searched
     * @param offset Number of first message to return
     * @param count Messages count to return
     * @return Count of messages from offset.
     */
    List<Message> findCheckupMessages(Checkup checkup, int offset, int count);

    /**
     * Finds check-up message resources.
     *
     * @param checkup Check-up to be searched
     * @return List of check-up message resources.
     */
    List<String> findCheckupMessageResources(Checkup checkup);

    /**
     * Finds check-up messages from given resource.
     *
     * @param checkup Check-up to be searched
     * @param resource Resource to be searched
     * @return Check-up messages from given resource.
     */
    List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource);

    /**
     * Finds check-up messages from given resource.
     *
     * @param checkup Check-up to be searched
     * @param resource Resource to be searched
     * @param offset Number of first message to return
     * @param count Count of messages to return
     * @return Count of check-up messages from given resource from offset.
     */
    List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource, int offset, int count);

    /**
     * Finds check-up messages from given resources.
     *
     * @param checkup Check-up to be searched
     * @param resources Resources to be searched
     * @return Check-up messages from given resources.
     */
    List<Message> findCheckupMessagesWithResources(Checkup checkup, List<String> resources);

    /**
     * Finds check-up messages from given resources.
     *
     * @param checkup Check-up to be searched
     * @param resources Resources to be searched
     * @param offset Number of first message to return
     * @param count Count of messages to return
     * @return Count of check-up messages from given resources from offset.
     */
    List<Message> findCheckupMessagesWithResources(Checkup checkup, List<String> resources, int offset, int count);

    List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators);

    List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators, int offset, int count);

    List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators);

    List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators, int offset, int count);

    /**
     * Flushes tables into database.
     */
    void flush();

    /**
     * Counts messages from check-up.
     *
     * @param checkup Check-up to be searched
     * @return Number of messages.
     */
    int countCheckupMessages(Checkup checkup);

    /**
     * Counts check-up messages from selected resources.
     *
     * @param checkup Check-up to be searched
     * @param resources Resources to be searched
     * @return Number of messages from selected resources.
     */
    int countCheckupMessagesWithResources(Checkup checkup, List<String> resources);

    int countCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators);

    int countCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators);
}
