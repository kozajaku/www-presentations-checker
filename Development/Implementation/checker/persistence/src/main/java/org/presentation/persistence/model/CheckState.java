package org.presentation.persistence.model;

/**
 *
 * @author radio.koza
 */
public enum CheckState {

    CREATED,
    PENDING,
    STOPPED_BEFORE_START,
    STOPPED_AFTER_START,
    CHECKING,
    FINISHED
}
