package org.presentation.utils;

import java.util.Set;

/**
 *
 * @author Jindřich Máca
 */
public class OptionContainer {

    private Set<String> chosenOptions;

    public Set<String> getChosenOptions() {
        return chosenOptions;
    }

    public void addOption(String option) {
        this.chosenOptions.add(option);
    }

}
