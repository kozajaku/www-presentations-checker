package org.presentation.model;

import java.util.Objects;

/**
 * This class represents the URL web address.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class LinkURL {

    //Text representaion of the URL web address
    private final String url;

    /**
     * Creates new instance of {@link LinkURL}.
     *
     * @param url {@link String} representation of the URL web address
     */
    public LinkURL(String url) {
        this.url = url;
    }

    /**
     * Returns the {@link String} representation of {@link LinkURL}.
     *
     * @return {@link String} representaion of the URL web address
     */
    public String getUrl() {
        return url;
    }

    /**
     * Checks if {@link LinkURL} starts with http or https protokol clause.
     *
     * @return {@code true} if {@link String} representaion of the URL starts
     * with "http://" or "https://" protokol clause; {@code false} otherwise
     */
    public boolean checkURL() {
        if (url.length() > 7) {
            if (url.substring(0, 7).equals("http://") || url.substring(0, 8).equals("https://")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return url.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LinkURL other = (LinkURL) obj;
        return Objects.equals(this.url, other.url);
    }

}
