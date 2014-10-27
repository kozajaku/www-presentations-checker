/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import java.util.List;

/**
 *
 * @author petrof
 */
public class CSSRuleSet {

    protected String selectorString;
    protected List<CSSRule> cssRules;

    public CSSRuleSet(String selectorString, List<CSSRule> cssRules) {
	this.cssRules = cssRules;
	this.selectorString = selectorString;
    }

    public String getSelectorString() {
	return selectorString;
    }

    public List<CSSRule> getCssRules() {
	return cssRules;
    }        
    
    public boolean isRedundant() {
	for(CSSRule rule : cssRules) {
	    if(!rule.isRedundant()) return false;
	}
	return true;
    }
    
}
