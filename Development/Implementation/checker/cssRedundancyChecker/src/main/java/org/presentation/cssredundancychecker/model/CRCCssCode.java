/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import org.presentation.parser.CSSCode;

/**
 *
 * @author petrof
 */
public class CRCCssCode {
    
    protected CSSCode cssCode;

    public CRCCssCode(CSSCode cssCode) {
	this.cssCode = cssCode;
    }

    public CSSCode getCssCode() {
	return cssCode;
    }      
    
    
}
