package org.presentation.model;

/**
 * This class represents the Content-Type attribute in HTTP head.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class ContentType {

    //Text representaion of Content-Type
    private final String contentType;

    /**
     * Creates new instance of {@link org.presentation.model.ContentType}.
     *
     * @param contentType {@link java.lang.String} representaion of HTTP
     * attribute Content-Type
     */
    public ContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Returns the {@link java.lang.String} representation of
     * {@link org.presentation.model.ContentType}.
     *
     * @return {@link java.lang.String} representaion of HTTP attribute
     * Content-Type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Check if {@link org.presentation.model.ContentType} is CSS.
     *
     * @return {@code true} if {@link java.lang.String} representaion of
     * {@link org.presentation.model.ContentType} is equal to "text/css";
     * {@code false} otherwise
     */
    public boolean isCss() {
        switch (contentType) {
            case "text/css":
                return true;
        }
        return false;
    }

    /**
     * Check if {@link org.presentation.model.ContentType} is HTML.
     *
     * @return {@code true} if {@link java.lang.String} representaion of
     * {@link org.presentation.model.ContentType} is equal to "text/html";
     * {@code false} otherwise
     */
    public boolean isHtml() {
        switch (contentType) {
            case "text/html":
                return true;
        }
        return false;
    }

    /**
     * Decides if {@link org.presentation.model.ContentType} {@link #isCss()} or
     * {@link #isHtml()} and can be processed futher.
     *
     * @return {@code true} if {@link java.lang.String} representaion of
     * {@link org.presentation.model.ContentType} is equal to "text/css" or
     * "text/html"; {@code false} otherwise
     */
    public boolean isProcessable() {
        return this.isHtml() || this.isCss();
    }
}
