/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.CombinedSelector;
import cz.vutbr.web.css.Declaration;
import cz.vutbr.web.css.RuleBlock;
import cz.vutbr.web.css.RuleSet;
import cz.vutbr.web.css.StyleSheet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.presentation.parser.CSSCode;

/**
 *
 * @author petrof
 */
public class CRCCssCode {
    
    protected CSSCode cssCode;
    protected List<CSSRuleSet> cssRuleBlocks;
    protected Map<DeclarationPosition,DeclarationSource> declarationPositionMap;
    
    public CRCCssCode(CSSCode cssCode) throws CSSException {
	this.cssCode = cssCode;
	this.cssRuleBlocks = new ArrayList<>();
	this.declarationPositionMap = new HashMap<>();
	loadRuleSets();
    }

    public CSSCode getCssCode() {
	return cssCode;
    }      
    
    /**
     * This method fills up inner structures
     * @throws CSSException 
     */
    private void loadRuleSets() throws CSSException {	
	StyleSheet stylesheet = cssCode.getParsedCSS();
	for(RuleBlock block : stylesheet) {
	    if(block instanceof RuleSet) {
		
		StringBuilder selectorString = new StringBuilder();
		RuleSet ruleSet = (RuleSet) block;	
						
		List<CombinedSelector> selectors = ruleSet.getSelectors();
		for(CombinedSelector selector : selectors) {
		    selectorString.append(selector.toString());
		    selectorString.append(", ");
		}	
		
		List<CSSRule> cssRules = new ArrayList<>();		
		CSSRule cssRule;
		Declaration.Source source;
		DeclarationPosition declarationPosition;			
		
		for(Declaration declaration : ruleSet) {
		    cssRule = new CSSRule(declaration.getProperty(), "");
		    
		    source = declaration.getSource();
		    if(source == null) {
			declarationPosition = null;			
		    } else {
			declarationPosition = new DeclarationPosition(source.getLine(), source.getPosition());
		    }
		    cssRule.setDeclarationPosition(declarationPosition);
		    
		    cssRules.add(cssRule);	    
		}
		
		CSSRuleSet cssRuleSet = new CSSRuleSet(selectorString.toString(), cssRules);
		
		this.cssRuleBlocks.add(cssRuleSet);
		
		// add record to the position-declaration map
		for(CSSRule iCssRule : cssRuleSet.getCssRules()) {
		    this.declarationPositionMap.put(
			    iCssRule.getDeclarationPosition(), 
			    new DeclarationSource(cssRuleSet, iCssRule)
		    );
		}
	    }
	}
    }
        
    public CSSRule getCssRuleByPosition(DeclarationPosition position) {
	if(this.declarationPositionMap.containsKey(position)) {
	    DeclarationSource declarationSource = this.declarationPositionMap.get(position);
	    if(declarationSource != null) {
		return declarationSource.getCssRule();
	    }
	}
	return null;
    }

    public List<CSSRuleSet> getCssRuleBlocks() {
	return cssRuleBlocks;
    }
        
    
}
