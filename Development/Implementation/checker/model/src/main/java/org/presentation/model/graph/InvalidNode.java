package org.presentation.model.graph;

import org.presentation.model.LinkURL;
import org.presentation.model.logging.ErrorCode;

/**
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
     * @param url
     * @param errorCode
     */
    public InvalidNode(LinkURL url, ErrorCode errorCode) {
        this.url = url;
        this.inputDegree = 0;
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
