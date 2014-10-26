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
 * @version 1.0-SNAPSHOT
 */
public class CSSCode implements ParsedCode {

    private final PageContent codeCSS;
    private final LinkURL linkCSS;
    private StyleSheet parsedCSS;

    /**
     * Creates {@link org.presentation.parser.CSSCode} without parsing.
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
     * Parses {@link PageContent} into {@link StyleSheet}. Don't call this
     * method if you don't need parsed code.
     *
     * @throws cz.vutbr.web.css.CSSException
     */
    private void parse() throws CSSException {

        try {
            this.parsedCSS = CSSFactory.parse(this.codeCSS.getContent());
        } catch (IOException ex) {
            // this is probably a bug in JStyleParser, when string is given to the parse method, it should not throw IOException in any cases!
        }

    }

    /**
     * <p>
     * Getter for the field <code>codeCSS</code>.</p>
     *
     * @return a {@link org.presentation.model.PageContent} object.
     */
    public PageContent getCodeCSS() {
        return codeCSS;
    }

    /**
     * <p>
     * Getter for the field <code>linkCSS</code>.</p>
     *
     * @return a {@link org.presentation.model.LinkURL} object.
     */
    public LinkURL getLinkCSS() {
        return linkCSS;
    }

    /**
     * Gets the parsed document. If the document hasn't been already parsed, it
     * gets parsed within this call.
     *
     * @return StyleSheet definition according to JStyleParser definition
     * @throws cz.vutbr.web.css.CSSException if any.
     */
    public StyleSheet getParsedCSS() throws CSSException {
        if (parsedCSS == null) {
            this.parse();
        }
        return parsedCSS;
    }

    @Override
    public ParsedCodeType getType() {
        return ParsedCodeType.CSS_CODE;
    }

}
