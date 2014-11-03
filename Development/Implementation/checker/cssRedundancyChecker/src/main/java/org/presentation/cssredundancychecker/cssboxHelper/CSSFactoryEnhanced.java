/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.cssboxHelper;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.StyleSheet;
import cz.vutbr.web.csskit.antlr.CSSParserFactory;
import cz.vutbr.web.csskit.antlr.CSSParserFactory.SourceType;
import java.io.IOException;
import java.net.URL;

class CSSFactoryEnhanced {

    /**
     * <p>
     * parse.</p>
     *
     * @param css a {@link java.lang.String} object.
     * @param base a {@link java.net.URL} object.
     * @return a {@link cz.vutbr.web.css.StyleSheet} object.
     * @throws java.io.IOException if any.
     * @throws cz.vutbr.web.css.CSSException if any.
     */
    public static final StyleSheet parse(String css, URL base) throws IOException, CSSException {
        return CSSParserFactory.parse(css, null, SourceType.EMBEDDED, base);
    }
}
