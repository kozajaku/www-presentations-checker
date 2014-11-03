/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import org.presentation.model.LinkURL;

/**
 * <p>
 * CSSRuleUsage class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class CSSRuleUsage {

    protected LinkURL url;

    /**
     * <p>
     * Constructor for CSSRuleUsage.</p>
     *
     * @param url a {@link org.presentation.model.LinkURL} object.
     */
    public CSSRuleUsage(LinkURL url) {
        this.url = url;
    }

    /**
     * <p>
     * Getter for the field <code>url</code>.</p>
     *
     * @return a {@link org.presentation.model.LinkURL} object.
     */
    public LinkURL getUrl() {
        return url;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return url.toString();
    }

}
