/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.presentation.model.LinkURL;
import org.presentation.parser.HTMLCode;

/**
 * <p>
 * CRCHtmlCode class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class CRCHtmlCode {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    protected List<LinkURL> stylesheetFilesRequired;
    protected HTMLCode htmlCode;

    /**
     * <p>
     * Constructor for CRCHtmlCode.</p>
     *
     * @param htmlCode a {@link org.presentation.parser.HTMLCode} object.
     */
    public CRCHtmlCode(HTMLCode htmlCode) {
        this.htmlCode = htmlCode;
        this.stylesheetFilesRequired = new ArrayList<>();
        this.loadStylesheetsRequiredFromDocument();
    }

    /**
     * <p>
     * Getter for the field <code>htmlCode</code>.</p>
     *
     * @return a {@link org.presentation.parser.HTMLCode} object.
     */
    public HTMLCode getHtmlCode() {
        return htmlCode;
    }

    /**
     * <p>
     * Getter for the field <code>stylesheetFilesRequired</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<LinkURL> getStylesheetFilesRequired() {
        return stylesheetFilesRequired;
    }

    /**
     * This method retrieves required stylesheet URLs from the document. The
     * URLs are retrieved from &lt;link&gt; tags.
     */
    private void loadStylesheetsRequiredFromDocument() {
        Elements links = this.htmlCode.getParsedHTML().select("link[href]");
        for (Element link : links) {
            String type = link.attr("type");
            String rel = link.attr("rel");
            if ((type != null && (type.toLowerCase().equals("text/css"))) || (rel != null && (rel.toLowerCase().equals("stylesheet")))) {
                String cssHref = link.attr("abs:href");
                if (cssHref != null) {
                    cssHref = cssHref.replaceAll("[/][.]+", "");  // normalize the path (ex http://example.com/./style.css -> http://example.com/style.css)
                    LinkURL stylesheetResource = new LinkURL(cssHref.split("#")[0]);
                    if (LOG != null) {
                        LOG.log(Level.INFO, "CSSRC - found CSS: {0}", stylesheetResource.getUrl());
                    }
                    if (stylesheetResource.checkURL()) {
                        this.stylesheetFilesRequired.add(stylesheetResource);
                    }
                }
            }
        }
    }

}
