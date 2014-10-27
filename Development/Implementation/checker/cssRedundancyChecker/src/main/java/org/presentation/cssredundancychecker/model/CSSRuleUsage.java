/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import org.presentation.model.LinkURL;

/**
 *
 * @author petrof
 */
public class CSSRuleUsage {
    protected LinkURL url;

    public CSSRuleUsage(LinkURL url) {
	this.url = url;
    }

    public LinkURL getUrl() {
	return url;
    }

    @Override
    public String toString() {
	return url.toString();
    }    
    
}
