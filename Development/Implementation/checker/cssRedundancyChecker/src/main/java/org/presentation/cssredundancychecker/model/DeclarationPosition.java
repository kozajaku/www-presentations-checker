/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

/**
 *
 * @author petrof
 */
public class DeclarationPosition {
    
    protected int line;
    protected int col;

    public DeclarationPosition(int line, int col) {
	this.line = line;
	this.col = col;
    }

    @Override
    public String toString() {
	return line + ":" + col;
    }
    
    
    @Override
    public int hashCode() {
	int hash = 5;
	hash = 29 * hash + this.line;
	hash = 29 * hash + this.col;
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final DeclarationPosition other = (DeclarationPosition) obj;
	if (this.line != other.line) {
	    return false;
	}
	if (this.col != other.col) {
	    return false;
	}
	return true;
    }

  
	    
    
}
