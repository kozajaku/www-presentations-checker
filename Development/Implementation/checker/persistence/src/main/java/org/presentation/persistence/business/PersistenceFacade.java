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
 * Interface which serves as middle business side between modules, which
 * requires ability to query data from and to database, and the specific
 * implementation of implementation of integration DAO classes.
 *
 * @author radio.koza
 */
public interface PersistenceFacade {

    //======================user methods========================================
    /**
     * Method creates new user in database by using email, password, user's name
     * and surname. Implementation should be cappable to work with salts and
     * hashing if it is specified so in database model.
     *
     * @param email Email of the new user
     * @param pass Password of the new user given as plain text in utf-8
     * encoding
     * @param name First name of the new user
     * @param surname Surname of the new user
     * @return <code>true</code> if user was created sucessfully;
     * <code>false</code> otherwise
     */
    boolean createNewUser(String email, String pass, String name, String surname);

    /**
     * Method finds user in database by user's email address (primary key). If
     * there is no user with specified email, method returns <code>null</code>.
     *
     * @param email Email of the user to be seeked
     * @return User with matching email or <code>null</code> if there is no user
     * with such email address
     */
    User findUser(String email);

    /**
     * Method edits user matching the specified email address. Note that this
     * method should not be used to update collection values inside {@link User}
     * class but should use proper methods instead. Editing of collection is not
     * necessary functionable because of JPA owning side problem.
     *
     * @param user Specific instance of {@link User} class defining the new
     * state of user with same email address
     */
    void editUser(User user);

    /**
     * Method adds new {@link Link} object to the specified user passed as
     * parameter. {@link Link} is created by using contemporary date and address
     * (IP) passed in parameter.
     *
     * @param user {@link User} instance which new {@link Login} should be added
     * to
     * @param address {@link String} representation of IP address from which is
     * user logged in
     */
    void addUserLogin(User user, String address);

    /**
     * Method finds {@link Login} instance of the specified user's last login.
     * If there is no user with such email address or found user does have no
     * logins, method returns {@code null}.
     *
     * @param user Instance of {@link User} that the {@link Login} should be
     * found for
     * @return Last Instance of {@link Login} representing last user login;
     * {@code null} if user not found or user have no logins
     */
    Login findLastUserLogin(User user);

    /**
     * Method finds collection of all user logins (instance of {@link Login}) of
     * the user specified as method parameter.
     *
     * @param user {@link User} that {@link Login} collection should be found
     * for
     * @return Collection of user logins; collection can be empty if user was
     * not found in database or user does have no logins; never returns
     * {@code null}
     */
    List<Login> findUserLogins(User user);

    //========================checkup methods===================================
    /**
     * Method creates new checkup by using instance of {@link Checkup} class.
     * Note that checkup passed as parameter must have set mandatory database
     * field and also {@link User} instance set through method {@link Checkup#setUser(org.presentation.persistence.model.User) }.
     *
     * @param checkup New checkup that should be persisted in database
     */
    void createNewCheckup(Checkup checkup);

    /**
     * Method updates checkup passed as parameter. Checkup is found by primary key
     * specified in passed instance of {@link Checkup} and merged to database with new values.
     * Note that this method should not be used for updating collection of {@link Checkup}.
     *
     * @param checkup Instance of {@link Checkup} to be merged into database with new values
     */
    void updateCheckup(Checkup checkup);

    /**
     * Method finds collection of {@link Checkup} class. Collection can be empty
     * if no checkup was found but collection is never {@code null}.
     *
     * @param user {@link User} for which to find collection of checkups
     * @return Collection of user's {@link Checkup}
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

    /**
     * <p>
     * findCheckupInitializedInputs.</p>
     *
     * @param checkId a {@link java.lang.Integer} object.
     * @return a {@link org.presentation.persistence.model.Checkup} object.
     */
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

    //=========================headers==========================================
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

    //=============================options======================================
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

    /**
     * <p>
     * findCheckupMessagesWithDiscriminators.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup}
     * object.
     * @param discriminators a {@link java.util.List} object.
     * @return a {@link java.util.List} object.
     */
    List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators);

    /**
     * <p>
     * findCheckupMessagesWithDiscriminators.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup}
     * object.
     * @param discriminators a {@link java.util.List} object.
     * @param offset a int.
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
    List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators, int offset, int count);

    /**
     * <p>
     * findCheckupMessagesWithResourcesDiscriminators.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup}
     * object.
     * @param resources a {@link java.util.List} object.
     * @param discriminators a {@link java.util.List} object.
     * @return a {@link java.util.List} object.
     */
    List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators);

    /**
     * <p>
     * findCheckupMessagesWithResourcesDiscriminators.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup}
     * object.
     * @param resources a {@link java.util.List} object.
     * @param discriminators a {@link java.util.List} object.
     * @param offset a int.
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
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

    /**
     * <p>
     * countCheckupMessagesWithDiscriminators.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup}
     * object.
     * @param discriminators a {@link java.util.List} object.
     * @return a int.
     */
    int countCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators);

    /**
     * <p>
     * countCheckupMessagesWithResourcesDiscriminators.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup}
     * object.
     * @param resources a {@link java.util.List} object.
     * @param discriminators a {@link java.util.List} object.
     * @return a int.
     */
    int countCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators);
}
