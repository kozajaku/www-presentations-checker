package org.presentation.model.graph;
import org.presentation.model.LinkURL;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public abstract class Node {

	protected LinkURL url;
	protected int inputDegree;

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