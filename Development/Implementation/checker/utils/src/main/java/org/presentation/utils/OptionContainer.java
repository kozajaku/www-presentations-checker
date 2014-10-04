package org.presentation.utils;

import java.util.List;

/**
 *
 * @author Jindřich Máca
 */
public class OptionContainer {

    private List<String> chosenOptions;

    public List<String> getChosenOptions() {
        return chosenOptions;
    }

    public void addOption(String option) {
        this.chosenOptions.add(option);
    }

}
