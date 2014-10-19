package org.presentation.model;

/**
 * This class represents the Content-Type attribute in HTTP head.
 *
 * @author Jindřich Máca
 */
public class ContentType {

    /**
     * Text representaion of Content-Type.
     */
    private final String contentType;

    /**
     * Constructs a Content-Type.
     *
     * @param contentType Text value of Content-Type.
     */
    public ContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Returns the text representation of Content-Type.
     *
     * @return Text representation of Content-Type.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Check if Content-Type is CSS.
     *
     * @return True if Content-Type is CSS.
     */
    public boolean isCss() {
        switch (contentType) {
            case "text/css":
                return true;
        }
        return false;
    }

    /**
     * Check if Content-Type is HTML.
     *
     * @return True if Content-Type is HTML.
     */
    public boolean isHtml() {
        switch (contentType) {
            case "text/html":
                return true;
        }
        return false;
    }

}
