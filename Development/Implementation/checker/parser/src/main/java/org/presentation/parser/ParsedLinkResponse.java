package org.presentation.parser;
import org.presentation.model.LinkURL;
import org.presentation.model.graph.LinkSourceType;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public class ParsedLinkResponse {

	private final LinkURL destination;
	private final LinkSourceType sourceType;
	private final String label;

    public ParsedLinkResponse(LinkURL destination, LinkSourceType sourceType, String label) {
        this.destination = destination;
        this.sourceType = sourceType;
        this.label = label;
    }

    public LinkURL getDestination() {
        return destination;
    }

    public LinkSourceType getSourceType() {
        return sourceType;
    }

    public String getLabel() {
        return label;
    }



}