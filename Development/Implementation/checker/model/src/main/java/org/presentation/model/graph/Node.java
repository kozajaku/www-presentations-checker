package org.presentation.model.graph;

import java.util.List;
import java.util.Objects;
import org.presentation.model.LinkURL;
import org.presentation.model.logging.ResponseCode;

/**
 * This class represents node in
 * {@link org.presentation.model.graph.TraversalGraph}.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public abstract class Node {

    /**
     * An absolute URL which is represented by node.
     */
    protected LinkURL url;
    /**
     * How many edges goes to node. Input degree from graph theory.
     */
    protected int inputDegree;

    /**
     * Says if node is valid or not.
     *
     * @return <code>true</code> if node is valid
     */
    public abstract boolean isValid();

    /**
     * Increase input degree by 1.
     */
    public void incInputDegree() {
        inputDegree++;
    }

    /**
     * <p>
     * getResponseCode.</p>
     *
     * @return a {@link org.presentation.model.logging.ResponseCode} object.
     */
    public abstract ResponseCode getResponseCode();

    /**
     * Gets oriented edges which goes from this node.
     *
     * @return List of oriented {@link org.presentation.model.graph.Edge}s or
     * empty collection if node has no succesors.
     */
    public abstract List<Edge> getOrientedEdges();

    /**
     * <p>
     * Getter for the field <code>url</code>.</p>
     *
     * @return a {@link org.presentation.model.LinkURL} object.
     */
    public LinkURL getUrl() {
        return url;
    }

    /**
     * <p>
     * Getter for the field <code>inputDegree</code>.</p>
     *
     * @return a int.
     */
    public int getInputDegree() {
        return inputDegree;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return url.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return url.equals(obj);
    }

}
