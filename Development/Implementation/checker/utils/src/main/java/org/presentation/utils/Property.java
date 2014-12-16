package org.presentation.utils;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The singleton class for querying constants from special property file
 * designed for application constants.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class Property {

    private Properties properties;

    private Property() {
        //singleton class
        properties = new Properties();
        try {
            properties.load(Property.class.getResourceAsStream("/constants.properties"));
        } catch (IOException ex) {
            Logger.getLogger(Property.class.getName()).log(Level.SEVERE, "Unable to load property file", ex);
        }
    }

    /**
     * <p>
     * getInstance.</p>
     *
     * @return a {@link org.presentation.utils.Property} object.
     */
    public static Property getInstance() {
        return PropertyHolder.INSTANCE;
    }

    private static class PropertyHolder {

        private static final Property INSTANCE = new Property();
    }

    /**
     * Query {@link java.lang.String} property by its name.
     *
     * @param propertyName Key - name of the property
     * @return String representing the result of the query
     * @throws java.util.NoSuchElementException if any.
     */
    public String getStringPropery(String propertyName) throws NoSuchElementException {
        String res = properties.getProperty(propertyName);
        if (res == null) {
            throw new NoSuchElementException("Key " + propertyName + " was not found in property file");
        }
        return res;
    }

    /**
     * Query integer property by its name.
     *
     * @param propertyName Key - name of the wanted property
     * @return integer value of property
     * @throws java.util.NoSuchElementException if any.
     */
    public int getIntProperty(String propertyName) throws NoSuchElementException {
        String res = getStringPropery(propertyName);
        int resInt;
        try {
            resInt = Integer.parseInt(res);
        } catch (NumberFormatException ex) {
            throw new NoSuchElementException("Key " + propertyName + " was FOUND but has invalid integer value: " + res);
        }
        return resInt;
    }
}
