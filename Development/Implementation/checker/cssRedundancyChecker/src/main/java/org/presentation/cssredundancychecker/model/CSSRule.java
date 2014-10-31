/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author petrof
 */
public class CSSRule {
    
    protected String name;
    protected String value;
    protected DeclarationPosition declarationPosition;
    protected List<CSSRuleUsage> cssRuleUsages;

    public CSSRule(String name, String value, DeclarationPosition declarationPosition) {
	this.name = name;
	this.value = value;
	this.declarationPosition = declarationPosition;
	this.cssRuleUsages = new ArrayList<>();
    }

    public CSSRule(String name, String value) {
	this.name = name;
	this.value = value;
	this.declarationPosition = null;
	this.cssRuleUsages = new ArrayList<>();
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
	return cssRuleUsages.isEmpty();
    }

    public void setDeclarationPosition(DeclarationPosition declarationPosition) {
	this.declarationPosition = declarationPosition;
    }

    public void addRuleUsage(CSSRuleUsage usage) {
	this.cssRuleUsages.add(usage);
    }

    public List<CSSRuleUsage> getCssRuleUsages() {
	return cssRuleUsages;
    }
    
    
            
    
}
