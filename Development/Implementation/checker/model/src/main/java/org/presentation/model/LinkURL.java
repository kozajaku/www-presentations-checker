package org.presentation.model;

import java.util.Objects;
import org.w3c.dom.Node;

/**
 * This class represents the URL web address.
 *
 * @author Jindřich Máca
 */
public class LinkURL {

    /**
     * Text representaion of the URL web address.
     */
    private final String url;

    /**
     * Constructs the URL web address.
     *
     * @param url Text value of the URL web address.
     */
    public LinkURL(String url) {
        this.url = url;
    }

    public LinkURL(Node hrefAttr) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the text representation of the URL web address.
     *
     * @return Text representaion of the URL web address.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Checks if the URL starts with http or https protokol clause.
     *
     * @return True if the URL starts with http or https protokol clause.
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
