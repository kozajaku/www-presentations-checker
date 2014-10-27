package org.presentation.persistence.model;

/**
 * Enum representing state of the checking persisted in the database table.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public enum CheckState {

    /**
     * Checkup is created and persisted in database, but it is not yet enqueued
     * in execution queue.
     */
    CREATED,
    /**
     * Checkup is enqueued in the execution queue.
     */
    PENDING,
    /**
     * Checkup was stopped before start of the checkup execution (stopped when
     * in state CREATED or PENDING).
     */
    STOPPED_BEFORE_START,
    /**
     * Checkup was stopped during checkup execution (stop called when in state
     * CHECKING). Even the checkup was stopped, the partially results taken
     * during execution should persist in database.
     */
    STOPPED_AFTER_START,
    /**
     * Checkup was taken by one of execution threads and checking is in
     * progress.
     */
    CHECKING,
    /**
     * Checkup was successfully finished and its results are persisted in the
     * database.
     */
    FINISHED;

    /**
     * Method checks that execution of checkup was ended.
     *
     * @return True if checking execution is ended; false otherwise
     */
    public boolean isEnded() {
        switch (this) {
            case FINISHED:
            case STOPPED_AFTER_START:
            case STOPPED_BEFORE_START:
                return true;
        }
        return false;
    }
}
