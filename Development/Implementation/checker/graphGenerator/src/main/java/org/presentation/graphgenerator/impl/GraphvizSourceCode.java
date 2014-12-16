/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import org.presentation.graphgenerator.GraphResult;

/**
 * This class represents some graphviz source code.
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
abstract public class GraphvizSourceCode extends GraphResult {

    public String sourceCode;

    /**
     * <p>
     * Constructor for GraphvizSourceCode.</p>
     *
     * @param sourceCode a {@link java.lang.String} object.
     */
    public GraphvizSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultAsCode() {
        return sourceCode;
    }

}
