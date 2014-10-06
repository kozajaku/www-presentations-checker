package org.presentation.model;

import java.util.Objects;

/**
 *
 * @author Jindřich Máca
 */
public class LinkURL {

    private final String url;

    public LinkURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    
    public boolean checkURL() {
        if (url.length() > 7) {
           if (url.substring(0,7).equals("http://") || url.substring(0,8).equals("https://")) {
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
