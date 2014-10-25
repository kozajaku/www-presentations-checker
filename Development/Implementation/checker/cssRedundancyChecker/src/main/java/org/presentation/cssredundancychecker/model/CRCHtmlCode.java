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
import org.presentation.model.graph.LinkSourceType;
import org.presentation.parser.HTMLCode;
import org.presentation.parser.ParsedLinkResponse;
import org.presentation.parser.helper.DOMBuilder;

/**
 *
 * @author petrof
 */
public class CRCHtmlCode {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    
    protected List<LinkURL> stylesheetFilesRequired;
    protected HTMLCode htmlCode;

    public CRCHtmlCode(HTMLCode htmlCode) {
	this.htmlCode = htmlCode;	
	this.loadStylesheetsRequiredFromDocument();
    }
    
    public HTMLCode getHtmlCode() {
	return htmlCode;
    }        

    public List<LinkURL> getStylesheetFilesRequired() {
	return stylesheetFilesRequired;
    }
        
    
    private void loadStylesheetsRequiredFromDocument() {
	Elements links = this.htmlCode.getParsedHTML().select("link[href]");
	for (Element link : links) {
            String type = link.attr("type");
	    if(type != null && type.equals("text/css")) {
		LinkURL stylesheetResource = new LinkURL(link.attr("abs:href").split("#")[0]);
		LOG.log(Level.INFO, "CSSRC - found CSS: {0}", stylesheetResource.getUrl());	    
		if (stylesheetResource.checkURL()) {
		    this.stylesheetFilesRequired.add(stylesheetResource);
		}
	    }
        }
    }
    
}
