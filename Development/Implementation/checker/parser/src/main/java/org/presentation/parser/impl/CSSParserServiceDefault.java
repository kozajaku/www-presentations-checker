package org.presentation.parser.impl;

import java.net.MalformedURLException;
import java.net.URL;
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
        List<ParsedLinkResponse> parsedLinks = new ArrayList<>();
        try {
            URL base = new URL(baseURL.getUrl());
            LOG.log(Level.INFO, "Parsing of CSS {0} started.", base.toString());
            if (sourceCode == null) {
                LOG.warning("sourceCode is null!");
                return parsedLinks;
            }
            Pattern URL_PATTERN = Pattern.compile("url\\([\",\']?(.*?)[\",\']?\\)");
            Matcher m = URL_PATTERN.matcher(sourceCode.getContent());
            while (m.find()) {
                LOG.log(Level.INFO, "Link found in CSS: {0}", m.group(1));
                try {
                    parsedLinks.add(new ParsedLinkResponse(formatToAbsURL(m.group(1), base), LinkSourceType.INSIDE_CSS, "")); //posible to fill label in form of CSS atribute
                } catch (MalformedURLException ex) {
                    LOG.log(Level.WARNING, "Can not convert relative URL to absolute: {1}", ex.getMessage());
                }
            }
            LOG.log(Level.INFO, "Parsing of CSS {0} finished.", baseURL.getUrl());
        } catch (MalformedURLException ex) {
            LOG.log(Level.WARNING, "Wrong baseURL: {0}", ex.getMessage());
        }
        return parsedLinks;
    }

    /**
     * Formats relative URL to absolute if necessary.
     *
     * @param url
     * @param baseURL
     * @return LinkURL
     */
    private LinkURL formatToAbsURL(String url, URL base) throws MalformedURLException {
        URL abs = new URL(base, url);
        return new LinkURL(abs.toString());
    }

//	/**
//	 * 
//	 * @param sourceCode
//	 */
//	public CSSSelectorTree parseWholeCSSPage(PageContent sourceCode){
//		return null;
//	}
}
