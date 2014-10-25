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
 * @version 1.0-SNAPSHOT
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
     * method should not be used to update collection values inside
     * {@link org.presentation.persistence.model.User} class but should use
     * proper methods instead. Editing of collection is not necessary
     * functionable because of JPA owning side problem.
     *
     * @param user Specific instance of
     * {@link org.presentation.persistence.model.User} class defining the new
     * state of user with same email address
     */
    void editUser(User user);

    /**
     * Method adds new {@link org.presentation.persistence.model.Login} object
     * to the specified user passed as parameter.
     * {@link org.presentation.persistence.model.Login} is created by using
     * contemporary date and address (IP) passed in parameter.
     *
     * @param user {@link org.presentation.persistence.model.User} instance
     * which new {@link org.presentation.persistence.model.Login} should be
     * added to
     * @param address {@link java.lang.String} representation of IP address from
     * which is user logged in
     */
    void addUserLogin(User user, String address);

    /**
     * Method finds {@link org.presentation.persistence.model.Login} instance of
     * the specified user's last login. If there is no user with such email
     * address or found user does have no logins, method returns {@code null}.
     *
     * @param user Instance of {@link org.presentation.persistence.model.User}
     * that the {@link org.presentation.persistence.model.Login} should be found
     * for
     * @return Last Instance of {@link org.presentation.persistence.model.Login}
     * representing last user login; {@code null} if user not found or user have
     * no logins
     */
    Login findLastUserLogin(User user);

    /**
     * Method finds collection of all user logins (instance of
     * {@link org.presentation.persistence.model.Login}) of the user specified
     * as method parameter.
     *
     * @param user {@link org.presentation.persistence.model.User} that
     * {@link org.presentation.persistence.model.Login} collection should be
     * found for
     * @return Collection of user logins; collection can be empty if user was
     * not found in database or user does have no logins; never returns
     * {@code null}
     */
    List<Login> findUserLogins(User user);

    //========================checkup methods===================================
    /**
     * Method creates new checkup by using instance of
     * {@link org.presentation.persistence.model.Checkup} class. Note that
     * checkup passed as parameter must have set mandatory database field and
     * also {@link org.presentation.persistence.model.User} instance set through
     * method {@link org.presentation.persistence.model.Checkup#setUser(org.presentation.persistence.model.User)
     *}.
     *
     * @param checkup New checkup that should be persisted in database
     */
    void createNewCheckup(Checkup checkup);

    /**
     * Method updates checkup passed as parameter. Checkup is found by primary
     * key specified in passed instance of
     * {@link org.presentation.persistence.model.Checkup} and merged to database
     * with new values. Note that this method should not be used for updating
     * collection of {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup Instance of
     * {@link org.presentation.persistence.model.Checkup} to be merged into
     * database with new values
     */
    void updateCheckup(Checkup checkup);

    /**
     * Method finds collection of
     * {@link org.presentation.persistence.model.Checkup} class of specified
     * {@link org.presentation.persistence.model.User}. Collection can be empty
     * if no checkup was found but collection is never {@code null}.
     *
     * @param user {@link org.presentation.persistence.model.User} for which to
     * find collection of checkups
     * @return Collection of user's
     * {@link org.presentation.persistence.model.Checkup}
     */
    List<Checkup> findUserCheckings(User user);

    /**
     * Method finds collection of
     * {@link org.presentation.persistence.model.Checkup} class of specified
     * {@link org.presentation.persistence.model.User}. Method uses attibutes
     * <code>offset</code> and <code>count</code> to limitate count and start
     * position on database query level.
     *
     * @param user Instance of {@link org.presentation.persistence.model.User}
     * to search checkups for
     * @param offset Index of first check-up to return (indexing from 0)
     * @param count Count of check-ups to return (could be fewer if specified
     * count of results is not in database)
     * @return Collection of {@link org.presentation.persistence.model.Checkup}
     * as query result; this is never <code>null</code> but could be empty
     */
    List<Checkup> findUserCheckings(User user, int offset, int count);

    /**
     * Method finds specific instance of
     * {@link org.presentation.persistence.model.Checkup} by specifiing its
     * primary key.
     *
     * @param checkId Primary key of
     * {@link org.presentation.persistence.model.Checkup} class
     * @return Instance of {@link org.presentation.persistence.model.Checkup}
     * with specified primary key; <code>null</code> if no such exists
     */
    Checkup findCheckup(Integer checkId);

    /**
     * Method returns instance of
     * {@link org.presentation.persistence.model.Checkup} class with initialized
     * input collections (header, option and domain lists).
     *
     * @param checkId Primary key of the wanted
     * {@link org.presentation.persistence.model.Checkup} class instance
     * @return {@link org.presentation.persistence.model.Checkup} object with
     * initialized input colletions; <code>null</code> if
     * {@link org.presentation.persistence.model.Checkup} with specified primary
     * key was not found
     */
    Checkup findCheckupInitializedInputs(Integer checkId);

    /**
     * Method finds collection of
     * {@link org.presentation.persistence.model.Checkup} filtered by not
     * finished state ordered by the state.
     *
     * @return Collection of unfinished
     * {@link org.presentation.persistence.model.Checkup} objects in order based
     * on state (checking, pending, created)
     */
    List<Checkup> findNotEndedCheckupsStateOrdered();

    /**
     * Method fetches instance of newly persisted
     * {@link org.presentation.persistence.model.Checkup} object.
     *
     * @return Instance of newly persisted checkup; <code>null</code> if no such
     * was found
     */
    Checkup fetchNewlyCreatedCheckup();

    /**
     * Counts instances of {@link org.presentation.persistence.model.Checkup}
     * class created by specified user.
     *
     * @param user Owner of checkups
     * @return Count of user's checkups
     */
    int countUserCheckups(User user);

    //=========================headers==========================================
    /**
     * Method persists collection of {@link org.presentation.model.Header} and
     * appends it to specified
     * {@link org.presentation.persistence.model.Checkup} object.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} to have
     * the {@link org.presentation.model.Header} collection appended
     * @param headers Collection of new {@link org.presentation.model.Header}
     * objects to be persisted
     */
    void addHeadersToCheckup(Checkup checkup, List<Header> headers);

    /**
     * Method finds collection of {@link org.presentation.model.Header} objected
     * assigned to specified {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * headers are assigned to
     * @return List of {@link org.presentation.model.Header}; collection could
     * be empty but never <code>null</code>
     */
    List<Header> findCheckupHeaders(Checkup checkup);

    //=============================options======================================
    /**
     * Method adds collection of options to specified
     * {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * new options should be assigned to
     * @param options Collection of new options that should be added to
     * {@link org.presentation.persistence.model.Checkup}
     */
    void addOptionsToCheckup(Checkup checkup, List<String> options);

    /**
     * Finds collection of
     * {@link org.presentation.persistence.model.ChosenOption} assigned to
     * specified {@link org.presentation.persistence.model.Checkup}
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * options are assigned to
     * @return List of {@link org.presentation.persistence.model.ChosenOption}
     * objects; collection could be empty if
     * {@link org.presentation.persistence.model.Checkup} does have no options;
     * never <code>null</code>
     */
    List<ChosenOption> findCheckupOptions(Checkup checkup);

    //========================domain============================================
    /**
     * Method assigns collection of {@link org.presentation.model.Domain} class
     * to specified {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * new domains should be assigned to
     * @param domains List of new {@link org.presentation.model.Domain} objects
     * that are assigned to {@link org.presentation.persistence.model.Checkup}
     */
    void addDomainsToCheckup(Checkup checkup, List<Domain> domains);

    /**
     * Finds collection of {@link org.presentation.model.Domain} classes
     * assigned to specific {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * domains are assigned to
     * @return List of {@link org.presentation.model.Domain} assigned to
     * specified {@link org.presentation.persistence.model.Checkup}; collection
     * could be empty of no such domain was found; list is never
     * <code>null</code>
     */
    List<Domain> findCheckupDomains(Checkup checkup);

    //============================graph=========================================
    /**
     * Method adds collection of
     * {@link org.presentation.persistence.model.Graph} to specified
     * {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * graphs should be assigned to
     * @param graphs Collection of new
     * {@link org.presentation.persistence.model.Graph} objects that should be
     * assigned to specified {@link org.presentation.persistence.model.Checkup}
     */
    void addGraphsToCheckup(Checkup checkup, List<Graph> graphs);

    /**
     * Method finds collection of
     * {@link org.presentation.persistence.model.Graph} assigned to specified
     * {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * wanted graphs are assigned to
     * @return Collection of {@link org.presentation.persistence.model.Graph}
     * objects that are assigned to specified
     * {@link org.presentation.persistence.model.Checkup}
     */
    List<Graph> findCheckupGraphs(Checkup checkup);

    /**
     * Returns list of names of graph types assigned to specified
     * {@link org.presentation.persistence.model.Checkup}. Checkup have always
     * at least one graph with specified graph type. There is possible to find
     * {@link org.presentation.persistence.model.Graph} of specified type with
     * specified {@link org.presentation.persistence.model.Checkup} by calling
     * method
     * {@link #findGraphByGraphType(org.presentation.persistence.model.Checkup, java.lang.String)}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * graph types should be found for
     * @return List of graph types represented by {@link java.lang.String} of
     * distinct graph types found in specific
     * {@link org.presentation.persistence.model.Checkup}
     */
    List<String> listGraphTypes(Checkup checkup);

    /**
     * Method finds {@link org.presentation.persistence.model.Graph} assigned to
     * specified Checkup filtered by graph type name. If no such graph is found,
     * method return <code>null</code>.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} to find
     * graph for
     * @param graphType Graph type name applied as filter
     * @return {@link org.presentation.persistence.model.Graph} matching
     * specified filter; <code>null</code> if no such graph exists
     */
    Graph findGraphByGraphType(Checkup checkup, String graphType);

    //==========================message=========================================
    /**
     * Method adds collection of {@link org.presentation.model.logging.Message}
     * object produced by specified <code>resource</code> to specified
     * {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * new messages should be added to
     * @param messages List of {@link org.presentation.model.logging.Message}
     * objects that should be persisted and assigned to specified
     * {@link org.presentation.persistence.model.Checkup}
     * @param resource Resource name of message producer (eg. Web Crawler,...)
     */
    void addMessagesToCheckup(Checkup checkup, List<Message> messages, String resource);

    /**
     * Method finds collection of all
     * {@link org.presentation.model.logging.Message} assigned to specified
     * {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be taken from
     * @return List of {@link org.presentation.model.logging.Message} objects
     * assigned to specified {@link org.presentation.persistence.model.Checkup}
     */
    List<Message> findCheckupMessages(Checkup checkup);

    /**
     * Method does the same functionality as
     * {@link #addMessagesToCheckup(org.presentation.persistence.model.Checkup, java.util.List, java.lang.String)},
     * but moreover the results are filtered by its position and limited in
     * count. Eg. method with <code>offset = 50</code> and
     * <code>count = 50</code> returns second fifty messages of the specified
     * {@link org.presentation.persistence.model.Checkup}. Optimization of this
     * method should be implemented in database layer and so this method should
     * have effective querying of data.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param offset Index of first message element (indexed from zero)
     * @param count Count of wanted messaged (method could return less than
     * count when there are not enough message elements in database)
     * @return List of {@link org.presentation.model.logging.Message} with
     * {@link java.util.List#size()} &lt;= <code>count</code>
     */
    List<Message> findCheckupMessages(Checkup checkup, int offset, int count);

    /**
     * Method lists all distinct names of message producers of specified
     * {@link org.presentation.persistence.model.Checkup}. (eg. Web Crawler,
     * ...). Resource name returned in list have at least on message logged in
     * checkup.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @return List of distinct message producer names that have at least one
     * message logged in checkup
     */
    List<String> findCheckupMessageResources(Checkup checkup);

    /**
     * Method finds collection of {@link org.presentation.model.logging.Message}
     * objects assigned to specified
     * {@link org.presentation.persistence.model.Checkup} and produced by
     * message producer represented by resource name.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param resource Name of message producer resource represented by
     * {@link java.lang.String}
     * @return List of {@link org.presentation.model.logging.Message} matching
     * specified filter
     */
    List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource);

    /**
     * Method has same functionality as
     * {@link #findCheckupMessagesWithResource(org.presentation.persistence.model.Checkup, java.lang.String)}
     * but moreover it is possible to filter results by indexing start location
     * and final count. Functionality is implemented on database query level and
     * so calling of this method is much more effective. This functionality is
     * useful for paginator in presentation tier.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param resource Name of the message producer represented by
     * {@link java.lang.String}
     * @param offset Index of the first message indexed from zero
     * @param count Count of messages wanted to query
     * @return List of {@link org.presentation.model.logging.Message} objects
     * matching filter where {@link java.util.List#size()} &lt;=
     * <code>count</code>
     */
    List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource, int offset, int count);

    /**
     * Method has same functionality as
     * {@link #findCheckupMessagesWithResource(org.presentation.persistence.model.Checkup, java.lang.String)}
     * but there can be used more message producer names to filter results.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param resources List of message producer names
     * @return List of {@link org.presentation.model.logging.Message} objects
     * matching the filter
     */
    List<Message> findCheckupMessagesWithResources(Checkup checkup, List<String> resources);

    /**
     * Method has same functionality as
     * {@link #findCheckupMessagesWithResources(org.presentation.persistence.model.Checkup, java.util.List)}
     * but moreover it is possible to filter results by indexing start location
     * and final count. Functionality is implemented on database query level and
     * so calling of this method is much more effective. This functionality is
     * useful for paginator in presentation tier.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param resources List of message producer names
     * @param offset Index of the first message indexed from zero
     * @param count Count of messages wanted to query
     * @return List of {@link org.presentation.model.logging.Message} objects
     * matching filter where {@link java.util.List#size()} &lt;=
     * <code>count</code>
     */
    List<Message> findCheckupMessagesWithResources(Checkup checkup, List<String> resources, int offset, int count);

    /**
     * Method finds collection of {@link org.presentation.model.logging.Message}
     * object assigned to specified
     * {@link org.presentation.persistence.model.Checkup} and filtered by
     * message discriminators. Discriminator is fully qualified name of the
     * {@link org.presentation.model.logging.Message} implementation. (eg.
     * org.presentation.model.logging.InfoMsg).
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param discriminators List of discriminators passed as instance of
     * {@link java.lang.String}
     * @return List of {@link org.presentation.model.logging.Message} objects
     * matching filter
     */
    List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators);

    /**
     * Method has same functionality as
     * {@link #findCheckupMessagesWithDiscriminators(org.presentation.persistence.model.Checkup, java.util.List)}
     * but moreover it is possible to filter results by indexing start location
     * and final count. Functionality is implemented on database query level and
     * so calling of this method is much more effective. This functionality is
     * useful for paginator purposes.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param discriminators List of message discriminators passed as instance
     * of {@link java.lang.String}
     * @param offset Index of the first message indexed from zero
     * @param count Count of messages wanted to query
     * @return List of {@link org.presentation.model.logging.Message} objects
     * matching filter where {@link java.util.List#size()} &lt;=
     * <code>count</code>
     */
    List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators, int offset, int count);

    /**
     * Method merges together functionality of
     * {@link #findCheckupMessagesWithDiscriminators(org.presentation.persistence.model.Checkup, java.util.List)}
     * and
     * {@link #findCheckupMessagesWithResources(org.presentation.persistence.model.Checkup, java.util.List)}.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param resources List of resource names represented by
     * {@link java.lang.String}
     * @param discriminators List of message discriminators represented by
     * {@link java.lang.String}
     * @return List of {@link org.presentation.model.logging.Message} objects
     * matching the filter
     */
    List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators);

    /**
     * Method has the same functionality as
     * {@link #findCheckupMessagesWithResourcesDiscriminators(org.presentation.persistence.model.Checkup, java.util.List, java.util.List)}
     * but moreover it is possible to filter results by indexing start location
     * and final count. Functionality is implemented on database query level and
     * so calling of this method is much more effective. This functionality is
     * useful for paginator purposes.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be found for
     * @param resources List of resource names represented by
     * {@link java.lang.String}
     * @param discriminators List of message discriminators represented by
     * {@link java.lang.String}
     * @param offset Index of the first message indexed from zero
     * @param count Count of messages wanted to query
     * @return List of {@link org.presentation.model.logging.Message} objects
     * matching the filter where {@link java.util.List#size()} &lt;=
     * <code>count</code>
     */
    List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators, int offset, int count);

    /**
     * Method flushes persistence context to database. This is useful when it is
     * necessary to assign primary keys to newly created entity classes and then
     * use the primary keys in the single transaction.
     */
    void flush();

    /**
     * Method counts {@link org.presentation.model.logging.Message} objects
     * assigned to specified {@link org.presentation.persistence.model.Checkup}.
     *
     * @param checkup Checkup that messages should be counted
     * @return Count of checkup's messages
     */
    int countCheckupMessages(Checkup checkup);

    /**
     * Counts messages assigned to checkup and filtered by resources.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be counted
     * @param resources List of message producer names serving as filter
     * @return Count of messages matching the filter
     */
    int countCheckupMessagesWithResources(Checkup checkup, List<String> resources);

    /**
     * Counts messages assigned to checkup and filtered by message
     * discriminators.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be counted
     * @param discriminators List of message discriminators serving as filter
     * @return Count of messages matching the filter
     */
    int countCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators);

    /**
     * Method merges functionality of
     * {@link #countCheckupMessagesWithResources(org.presentation.persistence.model.Checkup, java.util.List)}
     * and
     * {@link #countCheckupMessagesWithDiscriminators(org.presentation.persistence.model.Checkup, java.util.List)}
     * by using logical conjunction.
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} that
     * messages should be counted
     * @param resources List of message producer names serving as filter
     * @param discriminators List of message discriminators serving as filter
     * @return Count of messages matching the filter
     */
    int countCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators);
}
