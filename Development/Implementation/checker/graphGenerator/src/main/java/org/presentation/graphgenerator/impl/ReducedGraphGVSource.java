/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import org.presentation.utils.Property;

/**
 * This class represents graphviz source code from
 * {@link org.presentation.graphgenerator.impl.ReducedGraphGVSGenerator}.
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class ReducedGraphGVSource extends GraphvizSourceCode {

    /**
     * <p>
     * Constructor for ReducedGraphGVSource.</p>
     *
     * @param sourceCode a {@link java.lang.String} object.
     */
    public ReducedGraphGVSource(String sourceCode) {
        super(sourceCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultId() {
        return Property.getInstance().getStringPropery("ReducedGraphvizSource");
    }

}
