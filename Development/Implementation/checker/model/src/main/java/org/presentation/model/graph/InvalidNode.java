package org.presentation.model.graph;

import java.util.ArrayList;
import java.util.List;
import org.presentation.model.LinkURL;
import org.presentation.model.logging.ResponseCode;

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
    private final ResponseCode errorCode;

    /**
     * Creates invalid node.
     *
     * @param url An absolute URL representing link
     * @param errorCode Reason why the link is invalid
     */
    public InvalidNode(LinkURL url, ResponseCode errorCode) {
        this.url = url;
        this.inputDegree = 0;
        this.errorCode = errorCode;
    }

    public ResponseCode getResponseCode() {
        return errorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public List<Edge> getOrientedEdges() {
        return new ArrayList<>();
    }

}
