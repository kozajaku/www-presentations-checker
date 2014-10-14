package org.presentation.parser;
import org.presentation.model.LinkURL;
import org.presentation.model.graph.LinkSourceType;

/**
 * This class represents response from link parsers.
 * @author Adam Kugler
 * @version 1.0
 */
public class ParsedLinkResponse {

	private final LinkURL link;
	private final LinkSourceType sourceType;
	private final String label;

    /**
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

    public LinkURL getLink() {
        return link;
    }

    public LinkSourceType getSourceType() {
        return sourceType;
    }

    public String getLabel() {
        return label;
    }



}