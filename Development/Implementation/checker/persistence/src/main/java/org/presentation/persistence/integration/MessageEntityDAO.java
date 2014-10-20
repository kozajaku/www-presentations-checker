package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.MessageEntity;

/**
 * <p>MessageEntityDAO interface.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
public interface MessageEntityDAO {

    /**
     * <p>create.</p>
     *
     * @param message a {@link org.presentation.persistence.model.MessageEntity} object.
     */
    void create(MessageEntity message);

    /**
     * <p>findAllCheckMessages.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessages(Integer checkupId);

    /**
     * <p>findAllCheckMessages.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param offset a int.
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessages(Integer checkupId, int offset, int count);

    /**
     * <p>findAllCheckMessageResources.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    List<String> findAllCheckMessageResources(Integer checkupId);

    /**
     * <p>findAllCheckMessagesFromResource.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param resource a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource);

    /**
     * <p>findAllCheckMessagesFromResource.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param resource a {@link java.lang.String} object.
     * @param offset a int.
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessagesFromResource(Integer checkupId, String resource, int offset, int count);

    /**
     * <p>findAllCheckMessagesFromResources.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param resources a {@link java.util.List} object.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessagesFromResources(Integer checkupId, List<String> resources);

    /**
     * <p>findAllCheckMessagesFromResources.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param resources a {@link java.util.List} object.
     * @param offset a int.
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessagesFromResources(Integer checkupId, List<String> resources, int offset, int count);

    /**
     * <p>countCheckMessages.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a int.
     */
    int countCheckMessages(Integer checkupId);

    /**
     * <p>countCheckMessagesFromResources.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param resources a {@link java.util.List} object.
     * @return a int.
     */
    int countCheckMessagesFromResources(Integer checkupId, List<String> resources);

    /**
     * <p>countCheckMessagesByDiscriminators.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param discriminators a {@link java.util.List} object.
     * @return a int.
     */
    int countCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators);

    /**
     * <p>countCheckMessagesByResourcesDiscriminators.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param resources a {@link java.util.List} object.
     * @param discriminators a {@link java.util.List} object.
     * @return a int.
     */
    int countCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators);

    /**
     * <p>findAllCheckMessagesByDiscriminators.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param discriminators a {@link java.util.List} object.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators);

    /**
     * <p>findAllCheckMessagesByDiscriminators.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param discriminators a {@link java.util.List} object.
     * @param offset a int.
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessagesByDiscriminators(Integer checkupId, List<String> discriminators, int offset, int count);

    /**
     * <p>findAllCheckMessagesByResourcesDiscriminators.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param resources a {@link java.util.List} object.
     * @param discriminators a {@link java.util.List} object.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators);

    /**
     * <p>findAllCheckMessagesByResourcesDiscriminators.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @param resources a {@link java.util.List} object.
     * @param discriminators a {@link java.util.List} object.
     * @param offset a int.
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
    List<MessageEntity> findAllCheckMessagesByResourcesDiscriminators(Integer checkupId, List<String> resources, List<String> discriminators, int offset, int count);

}
