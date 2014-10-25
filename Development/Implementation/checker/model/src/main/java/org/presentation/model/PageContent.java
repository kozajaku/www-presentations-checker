package org.presentation.model;

/**
 * This class represents the text content of the specific page.
 *
 * @author Jindřich Máca
 * @version 1.0-SNAPSHOT
 */
public class PageContent {

    /**
     * Text representaion of the page content.
     */
    private final String content;

    /**
     * Constructs the page content.
     *
     * @param content Text representaion of the page content.
     */
    public PageContent(String content) {
        this.content = content;
    }

    /**
     * Returns the text representation of the page content.
     *
     * @return Text representation of the page content.
     */
    public String getContent() {
        return content;
    }

}
