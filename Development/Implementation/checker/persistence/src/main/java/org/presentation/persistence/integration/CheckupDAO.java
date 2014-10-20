package org.presentation.persistence.integration;

import java.util.List;
import org.presentation.persistence.model.CheckState;
import org.presentation.persistence.model.Checkup;

/**
 * <p>CheckupDAO interface.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
public interface CheckupDAO {

    /**
     * <p>create.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup} object.
     */
    void create(Checkup checkup);

    /**
     * <p>update.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup} object.
     */
    void update(Checkup checkup);

    /**
     * <p>find.</p>
     *
     * @param checkupId a {@link java.lang.Integer} object.
     * @return a {@link org.presentation.persistence.model.Checkup} object.
     */
    Checkup find(Integer checkupId);

    /**
     * <p>findAllUserChecks.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    List<Checkup> findAllUserChecks(String email);

    /**
     * <p>findAllUserChecks.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @param offset a int.
     * @param count a int.
     * @return a {@link java.util.List} object.
     */
    List<Checkup> findAllUserChecks(String email, int offset, int count);

    /**
     * <p>findAllWithState.</p>
     *
     * @param states an array of {@link org.presentation.persistence.model.CheckState} objects.
     * @return a {@link java.util.List} object.
     */
    List<Checkup> findAllWithState(CheckState[] states);

    /**
     * <p>flush.</p>
     */
    void flush();

    /**
     * <p>countUserChecks.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @return a int.
     */
    int countUserChecks(String email);
}
