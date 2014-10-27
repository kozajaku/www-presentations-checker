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
     * Creates new instance of {@link ContentType}.
     *
     * @param contentType {@link String} representaion of HTTP attribute
     * Content-Type
     */
    public ContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Returns the {@link String} representation of {@link ContentType}.
     *
     * @return {@link String} representaion of HTTP attribute Content-Type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Check if {@link ContentType} is CSS.
     *
     * @return {@code true} if {@link String} representaion of
     * {@link ContentType} is equal to "text/css"; {@code false} otherwise
     */
    public boolean isCss() {
        switch (contentType) {
            case "text/css":
                return true;
        }
        return false;
    }

    /**
     * Check if {@link ContentType} is HTML.
     *
     * @return {@code true} if {@link String} representaion of
     * {@link ContentType} is equal to "text/html"; {@code false} otherwise
     */
    public boolean isHtml() {
        switch (contentType) {
            case "text/html":
                return true;
        }
        return false;
    }

    /**
     * Decides if {@link ContentType} {@link #isCss()} or {@link #isHtml()} and
     * can be processed futher.
     *
     * @return {@code true} if {@link String} representaion of
     * {@link ContentType} is equal to "text/css" or "text/html"; {@code false}
     * otherwise
     */
    public boolean isProcessable() {
        return this.isHtml() || this.isCss();
    }
}
