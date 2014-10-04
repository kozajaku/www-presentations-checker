package org.presentation.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jindřich Máca
 */
public class OptionContainer {

    private final List<String> chosenOptions = new ArrayList<>();

    public List<String> getChosenOptions() {
        return chosenOptions;
    }

    public void addOption(String option) {
        this.chosenOptions.add(option);
    }

}
