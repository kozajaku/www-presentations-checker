/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

/**
 * <p>
 * DeclarationSource class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class DeclarationSource {

    private final CSSRuleSet cssRuleSet;
    private final CSSRule cssRule;

    /**
     * <p>
     * Constructor for DeclarationSource.</p>
     *
     * @param cssRuleSet a
     * {@link org.presentation.cssredundancychecker.model.CSSRuleSet} object.
     * @param cssRuleSet a
     * {@link org.presentation.cssredundancychecker.model.CSSRuleSet} object.
     * @param cssRule a
     * {@link org.presentation.cssredundancychecker.model.CSSRule} object.
     */
    public DeclarationSource(CSSRuleSet cssRuleSet, CSSRule cssRule) {
        this.cssRuleSet = cssRuleSet;
        this.cssRule = cssRule;
    }

    /**
     * <p>
     * Getter for the field <code>cssRuleSet</code>.</p>
     *
     * @return a {@link org.presentation.cssredundancychecker.model.CSSRuleSet}
     * object.
     */
    public CSSRuleSet getCssRuleSet() {
        return cssRuleSet;
    }

    /**
     * <p>
     * Getter for the field <code>cssRule</code>.</p>
     *
     * @return a {@link org.presentation.cssredundancychecker.model.CSSRule}
     * object.
     */
    public CSSRule getCssRule() {
        return cssRule;
    }

}
