package org.presentation.model;

/**
 * This class represents the text content of the specific page.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class PageContent {

    //Text representaion of the page content
    private final String content;

    /**
     * Creates new instance of {@link PageContent}.
     *
     * @param content {@link String} representaion of the page content
     */
    public PageContent(String content) {
        this.content = content;
    }

    /**
     * Returns the {@link String} representation of {@link PageContent}.
     *
     * @return {@link String} representaion of the page content
     */
    public String getContent() {
        return content;
    }

}
