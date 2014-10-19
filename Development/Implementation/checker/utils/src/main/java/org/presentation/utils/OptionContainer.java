package org.presentation.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents container for user options.
 *
 * @author Jindřich Máca
 */
public class OptionContainer {

    /**
     * Array list of user options.
     */
    private final List<String> chosenOptions = new ArrayList<>();

    /**
     * Returns list of all user options.
     *
     * @return List of all user options.
     */
    public List<String> getChosenOptions() {
        return chosenOptions;
    }

    /**
     * Add user option to this container.
     *
     * @param option User option.
     */
    public void addOption(String option) {
        this.chosenOptions.add(option);
    }

}
