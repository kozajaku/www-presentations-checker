package org.presentation.model.graph;

import org.presentation.model.LinkURL;
import org.presentation.model.logging.ErrorCode;

/**
 * This class represents invalid node (invalid link) in link graph.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public class InvalidNode extends Node {

    /**
     * Reason why the link is invalid.
     */
    private final ErrorCode errorCode;

    /**
     *
     * @param url an absolute URL representing link
     * @param errorCode reason why the link is invalid
     */
    public InvalidNode(LinkURL url, ErrorCode errorCode) {
        this.url = url;
        this.inputDegree = 0;
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
