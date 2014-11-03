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
     * Creates new instance of {@link org.presentation.model.PageContent}.
     *
     * @param content {@link java.lang.String} representaion of the page content
     */
    public PageContent(String content) {
        this.content = content;
    }

    /**
     * Returns the {@link java.lang.String} representation of
     * {@link org.presentation.model.PageContent}.
     *
     * @return {@link java.lang.String} representaion of the page content
     */
    public String getContent() {
        return content;
    }

}
