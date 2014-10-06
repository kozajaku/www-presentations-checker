package org.presentation.parser.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.LinkSourceType;
import org.presentation.parser.CSSParserService;
import org.presentation.parser.ParsedLinkResponse;

/**
 * Default implementation of CSSParserService
 *
 * @author Jindřich Máca
 * @version 1.0
 */
@Dependent
public class CSSParserServiceDefault implements CSSParserService {

    /**
     * Inject logger.
     */
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    /**
     * Default method for parse links from CSS file.
     *
     * @param sourceCode
     * @param baseURL
     * @return parsedLinks
     */
    @Override
    public List<ParsedLinkResponse> parseLinks(PageContent sourceCode, LinkURL baseURL) {
        LOG.log(Level.INFO, "Parsing of CSS {0} started.", baseURL.getUrl());
        List<ParsedLinkResponse> parsedLinks = new ArrayList<>();
        if (sourceCode == null) {
            LOG.warning("sourceCode is null!");
            return parsedLinks;
        }
        Pattern URL_PATTERN = Pattern.compile("url\\([\",\']?(.*?)[\",\']?\\)");
        Matcher m = URL_PATTERN.matcher(sourceCode.getContent());
        while (m.find()) {
            LOG.log(Level.INFO, "Link found in CSS: {0}", m.group(1));
            parsedLinks.add(new ParsedLinkResponse(formatToAbsURL(m.group(1), baseURL), LinkSourceType.INSIDE_CSS, "")); //posible to fill label in form of CSS atribute
        }
        LOG.log(Level.INFO, "Parsing of CSS {0} finished.", baseURL.getUrl());
        return parsedLinks;
    }

    /**
     * Formats relative URL to absolute if nessessary.
     *
     * @param url
     * @param baseURL
     * @return LinkURL
     */
    private LinkURL formatToAbsURL(String url, LinkURL baseURL) {
        //TODO: Decide if needs to be formated and if nessessary formate do absolute URL.
        return new LinkURL(url);
    }

//	/**
//	 * 
//	 * @param sourceCode
//	 */
//	public CSSSelectorTree parseWholeCSSPage(PageContent sourceCode){
//		return null;
//	}
}
