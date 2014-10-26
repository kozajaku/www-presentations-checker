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
public class CSSRule {
    
    protected String name;
    protected String value;
    protected DeclarationPosition declarationPosition;
    protected boolean redundant;

    public CSSRule(String name, String value, DeclarationPosition declarationPosition) {
	this.name = name;
	this.value = value;
	this.declarationPosition = declarationPosition;
	this.redundant = true;
    }

    public CSSRule(String name, String value) {
	this.name = name;
	this.value = value;
	this.declarationPosition = null;
	this.redundant = true;
    }        

    @Override
    public String toString() {
	return name + ": " + value;
    }        

    public String getName() {
	return name;
    }

    public String getValue() {
	return value;
    }

    public DeclarationPosition getDeclarationPosition() {
	return declarationPosition;
    }

    public boolean isRedundant() {
	return redundant;
    }

    public void setDeclarationPosition(DeclarationPosition declarationPosition) {
	this.declarationPosition = declarationPosition;
    }

    public void setRedundant(boolean redundant) {
	this.redundant = redundant;
    }
            
    
}
