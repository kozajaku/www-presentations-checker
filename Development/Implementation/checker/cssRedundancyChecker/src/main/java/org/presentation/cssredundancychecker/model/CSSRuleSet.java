/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import java.util.List;

/**
 * <p>
 * CSSRuleSet class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class CSSRuleSet {

    protected String selectorString;
    protected List<CSSRule> cssRules;

    /**
     * <p>
     * Constructor for CSSRuleSet.</p>
     *
     * @param selectorString a {@link java.lang.String} object.
     * @param cssRules a {@link java.util.List} object.
     */
    public CSSRuleSet(String selectorString, List<CSSRule> cssRules) {
        this.cssRules = cssRules;
        this.selectorString = selectorString;
    }

    /**
     * <p>
     * Getter for the field <code>selectorString</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSelectorString() {
        return selectorString;
    }

    /**
     * <p>
     * Getter for the field <code>cssRules</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<CSSRule> getCssRules() {
        return cssRules;
    }

    /**
     * <p>
     * isRedundant.</p>
     *
     * @return a boolean.
     */
    public boolean isRedundant() {
        for (CSSRule rule : cssRules) {
            if (!rule.isRedundant()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * <p>
     * isUnused.</p>
     *
     * @return a boolean.
     */
    public boolean isUnused() {
        for (CSSRule rule : cssRules) {
            if (!rule.isUnused()) {
                return false;
            }
        }
        return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return selectorString;
    }

}
