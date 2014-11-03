/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * CSSRule class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class CSSRule {

    protected String name;
    protected String value;
    protected DeclarationPosition declarationPosition;
    protected List<CSSRuleUsage> cssRuleUsages;

    /**
     * <p>
     * Constructor for CSSRule.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @param declarationPosition a
     * {@link org.presentation.cssredundancychecker.model.DeclarationPosition}
     * object.
     */
    public CSSRule(String name, String value, DeclarationPosition declarationPosition) {
        this.name = name;
        this.value = value;
        this.declarationPosition = declarationPosition;
        this.cssRuleUsages = new ArrayList<>();
    }

    /**
     * <p>
     * Constructor for CSSRule.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public CSSRule(String name, String value) {
        this.name = name;
        this.value = value;
        this.declarationPosition = null;
        this.cssRuleUsages = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name + ": " + value;
    }

    /**
     * <p>
     * Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Getter for the field <code>value</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getValue() {
        return value;
    }

    /**
     * <p>
     * Getter for the field <code>declarationPosition</code>.</p>
     *
     * @return a
     * {@link org.presentation.cssredundancychecker.model.DeclarationPosition}
     * object.
     */
    public DeclarationPosition getDeclarationPosition() {
        return declarationPosition;
    }

    /**
     * <p>
     * isRedundant.</p>
     *
     * @return a boolean.
     */
    public boolean isRedundant() {
        return cssRuleUsages.isEmpty();
    }

    /**
     * <p>
     * Setter for the field <code>declarationPosition</code>.</p>
     *
     * @param declarationPosition a
     * {@link org.presentation.cssredundancychecker.model.DeclarationPosition}
     * object.
     */
    public void setDeclarationPosition(DeclarationPosition declarationPosition) {
        this.declarationPosition = declarationPosition;
    }

    /**
     * <p>
     * addRuleUsage.</p>
     *
     * @param usage a
     * {@link org.presentation.cssredundancychecker.model.CSSRuleUsage} object.
     */
    public void addRuleUsage(CSSRuleUsage usage) {
        this.cssRuleUsages.add(usage);
    }

    /**
     * <p>
     * Getter for the field <code>cssRuleUsages</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<CSSRuleUsage> getCssRuleUsages() {
        return cssRuleUsages;
    }

}
