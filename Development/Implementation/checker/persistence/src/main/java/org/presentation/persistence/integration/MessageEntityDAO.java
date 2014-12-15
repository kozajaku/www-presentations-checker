package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.MessageEntity;

/**
 * MessageEntityDAO interface that serves as layer between business persistence
 * facade and persistence context.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface MessageEntityDAO {

    /**
     * Method persists new
     * {@link org.presentation.persistence.model.MessageEntity} in database.
     * Note that message must have checkup assigned or this method will fail.
     *
     * @param message {@link org.presentation.persistence.model.MessageEntity}
     * to be persisted in database
     */
    void create(MessageEntity message);

    /**
     * Method finds collection of all
     * {@link org.presentation.persistence.model.MessageEntity} objects assigned
     * to checkup with specified primary key.
     *
     * @param checkupId Primary key of the checkup
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * instances; collection can be empty but never <code>null</code>
     */
    List<MessageEntity> findAllCheckMessages(Integer checkupId);

    /**
     * Method has the same functionality as
     * {@link #findAllCheckMessages(java.lang.Integer)} but moreover supports
     * filtering result by specifying index of first result and count of
     * results.
     *
     * @param checkupId Primary key of the checkup
     * @param offset Index of the first element indexed from zero
     * @param count Count of the results to query
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * assigned to the checkup with {@link java.util.List#size()} &lt;=
     * <code>count</code>
     */
    List<MessageEntity> findAllCheckMessages(Integer checkupId, int offset, int count);

    /**
     * Method lists all distinct resource names of checkup specified by primary
     * key.
     *
     * @param checkupId Primary key of the checkup
     * @return Collection of distinct message producer names
     */
    List<String> findAllCheckMessageResources(Integer checkupId);

    /**
     * Method finds collection of
     * {@link org.presentation.persistence.model.MessageEntity} objects with
     * specified message producer assigned to checkup specified by primary key.
     *
     * @param checkupId Primary key of the checkup
     * @param resource Name of the message producer
     * @return List of the
     * {@link org.presentation.persistence.model.MessageEntity} instances
     */
    List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource);

    /**
     * Method has the same functionality as
     * {@link #findAllCheckMessagesFromResource(java.lang.Integer, java.lang.String)}
     * but moreover supports filtering results by specifying index of first
     * result and count of results.
     *
     * @param checkupId Primary key of the checkup
     * @param resource Name of the message producer
     * @param offset Index of the first element indexed from zero
     * @param count Count of the results to query
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * assigned to the checkup with {@link java.util.List#size()} &lt;=
     * <code>count</code>
     */
    List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource, int offset, int count);

    /**
     * Method has the same functionality as
     * {@link #findAllCheckMessagesFromResource(java.lang.Integer, java.lang.String)}
     * but supports multiple message producer names filtering.
     *
     * @param checkupId Primary key of the checkup
     * @param resources Collection of wanted message producer names
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * matching the filter
     */
    List<MessageEntity> findAllCheckMessagesFromResources(Integer checkupId, List<String> resources);

    /**
     * Method has the same functionality as
     * {@link #findAllCheckMessagesFromResources(java.lang.Integer, java.util.List)}
     * but moreover supports filtering results by specifying index of first
     * result and count of results.
     *
     * @param checkupId Primary key of the checkup
     * @param resources Collection of wanted message producer names
     * @param offset Index of the first element indexed from zero
     * @param count Count of the results to query
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * assigned to the checkup with {@link java.util.List#size()} &lt;=
     * <code>count</code>
     */
    List<MessageEntity> findAllCheckMessagesFromResources(Integer checkupId, List<String> resources, int offset, int count);

    /**
     * Method counts all
     * {@link org.presentation.persistence.model.MessageEntity} objects which
     * are assigned to checkup with specified primary key.
     *
     * @param checkupId Primary key of the checkup
     * @return Count of the messages
     */
    long countCheckMessages(Integer checkupId);

    /**
     * Method counts {@link org.presentation.persistence.model.MessageEntity}
     * objects matching one of specified message producer names assigned to
     * checkup specified by its primary key.
     *
     * @param checkupId Primary key of the specified checkup
     * @param resources Collection of wanted message producer names
     * @return Count of the
     * {@link org.presentation.persistence.model.MessageEntity} instances
     * matching the filter
     */
    long countCheckMessagesFromResources(Integer checkupId, List<String> resources);

    /**
     * Method counts {@link org.presentation.persistence.model.MessageEntity}
     * objects matching one of specified message discriminator names assigned to
     * checkup specified by its primary key.
     *
     * @param checkupId Primary key of the specified checkup
     * @param discriminators Collection of wanted message discriminators
     * (discriminator is fully qualified class name of
     * {@link org.presentation.model.logging.Message} abstract class
     * implementation)
     * @return Count of the
     * {@link org.presentation.persistence.model.MessageEntity} instances
     * matching the filter
     */
    long countCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators);

    /**
     * Method merges functionality of
     * {@link #countCheckMessagesFromResources(java.lang.Integer, java.util.List)}
     * and
     * {@link #countCheckMessagesByDiscriminators(java.lang.Integer, java.util.List)}.
     * Merge is made by usage of logical conjunction.
     *
     * @param checkupId Primary key of the checkup
     * @param resources Collection of wanted message producer names
     * @param discriminators Collection of wanted message discriminators
     * (discriminator is fully qualified class name of
     * {@link org.presentation.model.logging.Message} abstract class)
     * @return Count of the
     * {@link org.presentation.persistence.model.MessageEntity} instances
     * matching the filter
     */
    long countCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators);

    /**
     * Method finds all {@link org.presentation.persistence.model.MessageEntity}
     * objects of checkup specified by its primary key with one of specified
     * message discriminator names.
     *
     * @param checkupId Primary key of the checkup
     * @param discriminators Collection of wanted message discriminators
     * (discriminator is fully qualified class name of
     * {@link org.presentation.model.logging.Message} abstract class)
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * instances matching the filter
     */
    List<MessageEntity> findAllCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators);

    /**
     * Method has same functionality as
     * {@link #findAllCheckMessagesByDiscriminators(java.lang.Integer, java.util.List)}
     * but moreover supports filtering results by specifying index of first
     * result and count of results.
     *
     * @param checkupId Primary key of the checkup
     * @param discriminators Collection of wanted message discriminators
     * @param offset Index of the first element indexed from zero
     * @param count Count of the results to query
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * instances matching the filter
     */
    List<MessageEntity> findAllCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators, int offset, int count);

    /**
     * Method merges functionality of
     * {@link #findAllCheckMessagesFromResources(java.lang.Integer, java.util.List)}
     * and
     * {@link #findAllCheckMessagesByDiscriminators(java.lang.Integer, java.util.List)}.
     * Merge is made by usage of logical conjunction.
     *
     * @param checkupId Primary key of the checkup
     * @param resources Collection of wanted message producer names
     * @param discriminators Collection of wanted message discriminators
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * instances matching the filter
     */
    List<MessageEntity> findAllCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators);

    /**
     * Method has same functionality as
     * {@link #findAllCheckMessagesByResourcesDiscriminators(java.lang.Integer, java.util.List, java.util.List)}
     * but moreover supports filtering results by specifying index of first
     * result and count of results.
     *
     * @param checkupId Primary key of the checkup
     * @param resources Collection of wanted message producer names
     * @param discriminators Collection of wanted message discriminators
     * @param offset Index of the first element indexed from zero
     * @param count Count of the results to query
     * @return List of {@link org.presentation.persistence.model.MessageEntity}
     * instances matching the filterF
     */
    List<MessageEntity> findAllCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators, int offset, int count);

}
