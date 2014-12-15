/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

/**
 * This class represents graphviz source code from {@link FullGraphGVSGenerator}.
 * 
 * @author petrof
 */
public class FullGraphGVSource extends GraphvizSourceCode {

    public FullGraphGVSource(String sourceCode) {
	super(sourceCode);
    }

    @Override
    public String getResultId() {
	return "Full graph - graphviz source";
    }
    
}
