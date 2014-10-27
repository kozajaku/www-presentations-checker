package org.presentation.parser;

import org.presentation.model.LinkURL;
import org.presentation.model.graph.LinkSourceType;

/**
 * This class represents response from link parsers.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public class ParsedLinkResponse {

    private final LinkURL link;
    private final LinkSourceType sourceType;
    private final String label;

    /**
     * <p>
     * Constructor for ParsedLinkResponse.</p>
     *
     * @param link URL
     * @param sourceType type of link source
     * @param label label which represents link
     */
    public ParsedLinkResponse(LinkURL link, LinkSourceType sourceType, String label) {
        this.link = link;
        this.sourceType = sourceType;
        this.label = label;
    }

    /**
     * <p>
     * Getter for the field <code>link</code>.</p>
     *
     * @return a {@link org.presentation.model.LinkURL} object.
     */
    public LinkURL getLink() {
        return link;
    }

    /**
     * <p>
     * Getter for the field <code>sourceType</code>.</p>
     *
     * @return a {@link org.presentation.model.graph.LinkSourceType} object.
     */
    public LinkSourceType getSourceType() {
        return sourceType;
    }

    /**
     * <p>
     * Getter for the field <code>label</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getLabel() {
        return label;
    }

}
