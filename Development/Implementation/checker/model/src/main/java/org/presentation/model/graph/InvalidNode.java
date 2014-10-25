package org.presentation.model.graph;

import org.presentation.model.LinkURL;
import org.presentation.model.logging.ErrorCode;

/**
 * This class represents invalid node (invalid link) in
 * {@link org.presentation.model.graph.TraversalGraph}.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public class InvalidNode extends Node {

    /**
     * Reason why the link is invalid. HTTP error code.
     */
    private final ErrorCode errorCode;

    /**
     * Creates invalid node.
     *
     * @param url An absolute URL representing link
     * @param errorCode Reason why the link is invalid
     */
    public InvalidNode(LinkURL url, ErrorCode errorCode) {
        this.url = url;
        this.inputDegree = 0;
        this.errorCode = errorCode;
    }

    /**
     * <p>
     * Getter for the field <code>errorCode</code>.</p>
     *
     * @return a {@link org.presentation.model.logging.ErrorCode} object.
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return false;
    }

}
