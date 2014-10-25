/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.parser;

import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import cz.vutbr.web.css.StyleSheet;

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
     */
    public void parse() {
        //TO DO
    }
}
