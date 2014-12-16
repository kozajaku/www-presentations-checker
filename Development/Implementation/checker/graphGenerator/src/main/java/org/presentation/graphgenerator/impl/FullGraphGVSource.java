/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import org.presentation.utils.Property;

/**
 * This class represents graphviz source code from
 * {@link org.presentation.graphgenerator.impl.FullGraphGVSGenerator}.
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class FullGraphGVSource extends GraphvizSourceCode {

    /**
     * <p>
     * Constructor for FullGraphGVSource.</p>
     *
     * @param sourceCode a {@link java.lang.String} object.
     */
    public FullGraphGVSource(String sourceCode) {
        super(sourceCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultId() {
        return Property.getInstance().getStringPropery("FullGraphvizSource");
    }

}
