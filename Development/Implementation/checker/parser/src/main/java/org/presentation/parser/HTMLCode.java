/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.parser;

import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.w3c.dom.Document;

/**
 * This class is HTML code holder with possibility to parse.
 *
 * @author Adam
 */
public class HTMLCode {

    PageContent codeHTML;
    LinkURL linkHTML;
    Document parsedHTML;
    /**
     * Creates {@link HTMLCode} without parsing.
     * @param codeHTML HTML source code
     * @param linkHTML Link to HTML page
     * @see #parse() 
     */
    public HTMLCode(PageContent codeHTML, LinkURL linkHTML) {
        this.codeHTML = codeHTML;
        this.linkHTML = linkHTML;
        parsedHTML = null;
    }

    /**
     * Parses {@link PageContent} into {@link Document}. Don't call this method
     * if you don't need parsed code.
     */
    public void parse() {
        //TO DO
    }
}
