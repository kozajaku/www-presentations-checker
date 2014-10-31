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
public class DeclarationSource {

    private CSSRuleSet cssRuleSet;
    private CSSRule cssRule;

    public DeclarationSource(CSSRuleSet cssRuleSet, CSSRule cssRule) {
	this.cssRuleSet = cssRuleSet;
	this.cssRule = cssRule;
    }

    public CSSRuleSet getCssRuleSet() {
	return cssRuleSet;
    }

    public CSSRule getCssRule() {
	return cssRule;
    }
        
    
}

