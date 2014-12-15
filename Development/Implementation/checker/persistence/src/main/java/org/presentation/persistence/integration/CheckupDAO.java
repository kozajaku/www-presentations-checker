package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.CheckState;
import org.presentation.persistence.model.Checkup;

/**
 * CheckupDAO interface that serves as layer between business persistence facade
 * and persistence context.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public interface CheckupDAO {

    /**
     * Method persists new {@link org.presentation.persistence.model.Checkup} in
     * database. Note that user of the checkup must be specified in order to
     * successful persist data to database.
     *
     * @param checkup New {@link org.presentation.persistence.model.Checkup}
     * instance to be persisted to database
     */
    void create(Checkup checkup);

    /**
     * Method updates attributes of the specified
     * {@link org.presentation.persistence.model.Checkup}. Note that this method
     * should not be used to edit collections in checkup (eg. domains, headers,
     * etc...).
     *
     * @param checkup {@link org.presentation.persistence.model.Checkup} which
     * should be updated in database
     */
    void update(Checkup checkup);

    /**
     * Method finds instance of
     * {@link org.presentation.persistence.model.Checkup} in database by its
     * primary key.
     *
     * @param checkupId Primary key of the checkup which is going to be searched
     * @return Instance of {@link org.presentation.persistence.model.Checkup} if
     * found; <code>null</code> if checkup with specified primary key does not
     * exist in database
     */
    Checkup find(Integer checkupId);

    /**
     * Method returns collection of
     * {@link org.presentation.persistence.model.Checkup} objects assigned to
     * the user with specified email address.
     *
     * @param email Email of the user whose checkups should be found
     * @return List of {@link org.presentation.persistence.model.Checkup}
     * instances assigned to user with specified email; collection could be
     * empty if no such {@link org.presentation.persistence.model.Checkup}
     * exists; never returns <code>null</code>
     */
    List<Checkup> findAllUserChecks(String email);

    /**
     * Method has the same functionality as
     * {@link #findAllUserChecks(java.lang.String)} but moreover it is possible
     * to filter results by its starting index position and wanted results
     * count. This method is used for purposes of paginator.
     *
     * @param email Email of the user whose checkups should be found
     * @param offset Index of the
     * {@link org.presentation.persistence.model.Checkup} to limit query by
     * @param count Count of wanted results.
     * @return List of {@link org.presentation.persistence.model.Checkup}
     * instances matching filter with {@link java.util.List#size()} &lt;=
     * <code>count</code>.
     */
    List<Checkup> findAllUserChecks(String email, int offset, int count);

    /**
     * Method finds all {@link org.presentation.persistence.model.Checkup}
     * objects with one of specified states passed in parameter.
     *
     * @param states Array of states to be applied as filter
     * @return List of {@link org.presentation.persistence.model.Checkup}
     * matching the filter
     */
    List<Checkup> findAllWithState(CheckState[] states);

    /**
     * Method flushes persistence context to database. This is useful when it is
     * necessary to assign primary keys to newly created entity classes and then
     * use the primary keys in the single transaction.
     */
    void flush();

    /**
     * Method counts {@link org.presentation.persistence.model.Checkup} objects
     * assigned to user with specified email.
     *
     * @param email Email of the user whose checkups should be counted.
     * @return Count of user's checkups
     */
    long countUserChecks(String email);
}
