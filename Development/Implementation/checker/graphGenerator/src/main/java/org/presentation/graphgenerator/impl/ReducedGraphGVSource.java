/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

/**
 * This class represents graphviz source code from {@link ReducedGraphGVSGenerator}.
 * 
 * @author petrof
 */
public class ReducedGraphGVSource extends GraphvizSourceCode {

    public ReducedGraphGVSource(String sourceCode) {
	super(sourceCode);
    }
   
    @Override
    public String getResultId() {
	return "Reduced graph - graphviz source";
    }
    
}
