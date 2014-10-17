package org.presentation.utils;

/**
 *
 * @author radio.koza
 */
public interface AllowOptionService {

    /**
     * Method returns unique identifier representing the chosen option.
     * @return String unique identifier
     */
    public String getID();

    /**
     * Method returns description of this allowed option.
     *
     * @deprecated In future versions this method will be removed and
     * functionality should be replaced by localization from getID() in
     * presentation tier or by querying description from database.
     * @return String descripting selected option
     */
    public String getDestription();
}
