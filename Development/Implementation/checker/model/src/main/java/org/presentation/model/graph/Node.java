package org.presentation.model.graph;

import org.presentation.model.LinkURL;

/**
 * This class represents node in link graph.
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
     * How many edges goes to node.
     */
    protected int inputDegree;

    /**
     * Says if node is valid or not.
     *
     * @return true if node is valid
     */
    public abstract boolean isValid();

    /**
     * Increase input degree.
     */
    public void incInputDegree() {
        inputDegree++;
    }

    public LinkURL getUrl() {
        return url;
    }

    public int getInputDegree() {
        return inputDegree;
    }

}
