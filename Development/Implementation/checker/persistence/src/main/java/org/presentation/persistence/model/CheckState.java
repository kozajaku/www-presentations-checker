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
    FINISHED;

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
