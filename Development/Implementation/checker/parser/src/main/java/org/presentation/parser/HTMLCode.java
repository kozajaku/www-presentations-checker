/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.parser;

import org.jsoup.Jsoup;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.parser.helper.DOMBuilder;
import org.w3c.dom.Document;

/**
 * This class is HTML code holder with possibility to parse.
 *
 * @author Adam
 * @version 1.0-SNAPSHOT
 */
public class HTMLCode {

    PageContent codeHTML;
    LinkURL linkHTML;
    Document parsedHTML;

    /**
     * Creates {@link org.presentation.parser.HTMLCode} without parsing.
     *
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
    private void parse() {
        org.jsoup.nodes.Document jsoupDocument = Jsoup.parse(this.codeHTML.getContent());

        Document document = DOMBuilder.jsoup2DOM(jsoupDocument);

        this.parsedHTML = document;
    }

    /**
     * <p>
     * Getter for the field <code>codeHTML</code>.</p>
     *
     * @return a {@link org.presentation.model.PageContent} object.
     */
    public PageContent getCodeHTML() {
        return codeHTML;
    }

    /**
     * <p>
     * Getter for the field <code>linkHTML</code>.</p>
     *
     * @return a {@link org.presentation.model.LinkURL} object.
     */
    public LinkURL getLinkHTML() {
        return linkHTML;
    }

    /**
     * Gets the parsed document. If the document hasn't been already parsed, it
     * gets parsed within this call.
     *
     * @return org.w3c.dom.Document parsed HTML code
     */
    public Document getParsedHTML() {
        if (parsedHTML == null) {
            this.parse();
        }
        return parsedHTML;
    }

}
