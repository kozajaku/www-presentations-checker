/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.parser;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.CSSFactory;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import cz.vutbr.web.css.StyleSheet;
import java.io.IOException;

/**
 * This class is CSS code holder with possibility to parse.
 *
 * @author Adam
 */
public class CSSCode {

    private PageContent codeCSS;
    private LinkURL linkCSS;
    private StyleSheet parsedCSS;

    /**
     * Creates {@link CSSCode} without parsing.
     *
     * @param codeCSS CSS source code
     * @param linkCSS Link to CSS page
     * @see #parse()
     */
    public CSSCode(PageContent codeCSS, LinkURL linkCSS) {
        this.codeCSS = codeCSS;
        this.linkCSS = linkCSS;
        parsedCSS = null;
    }

 
    /**
     * Parses {@link PageContent} into {@link StyleSheet}. Don't call this method if you
     * don't need parsed code.
     * @throws cz.vutbr.web.css.CSSException
     */
    private void parse() throws CSSException {        	
	
	try {
	    this.parsedCSS = CSSFactory.parse(this.codeCSS.getContent());
	}
	catch(IOException ex) {
	    // this is probably a bug in JStyleParser, when string is given to the parse method, it should not throw IOException in any cases!
	}
	
    }

    public PageContent getCodeCSS() {
	return codeCSS;
    }

    public LinkURL getLinkCSS() {
	return linkCSS;
    }

    /**
     * Gets the parsed document. If the document hasn't been already parsed, it gets parsed within this call.
     * @return StyleSheet definition according to JStyleParser definition
     * @throws CSSException 
     */
    public StyleSheet getParsedCSS() throws CSSException {
	if(parsedCSS == null) this.parse();
	return parsedCSS;
    }
    
    
}
