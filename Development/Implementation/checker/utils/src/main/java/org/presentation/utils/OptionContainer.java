package org.presentation.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents container for user options.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class OptionContainer {

    //Array list of user options.
    private final List<String> chosenOptions = new ArrayList<>();

    /**
     * Returns {@link List} of all user options from this
     * {@link OptionContainer}.
     *
     * @return {@link List} of all user options
     */
    public List<String> getChosenOptions() {
        return chosenOptions;
    }

    /**
     * Add user option to this {@link OptionContainer}.
     *
     * @param option {@link String} representation of user option
     */
    public void addOption(String option) {
        this.chosenOptions.add(option);
    }

}
