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
 * Default implementation of {@link CSSParserService}. Parses given CSS
 * {@link PageContent} for {@link List} of {@link ParsedLinkResponse} using
 * fact, that they are included in "url(...)" syntax.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
@Dependent
public class CSSParserServiceDefault implements CSSParserService {

    //Inject logger
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    /**
     * {@inheritDoc}
     *
     * Default method for parse {@link ParsedLinkResponse} from the CSS
     * {@link PageContent}. Search in code for "url(...)" sytax and extract the
     * URL from it. Then it formats it to absolute URL form and save it to
     * {@link List} as instance of {@link ParsedLinkResponse}, that returns in
     * the end.
     *
     * @param sourceCode CSS {@link PageContent}
     * @param baseURL {@link LinkURL} of the parsed CSS file; it is used to
     * formats {@link ParsedLinkResponse} addresses to their absolute forms
     * @return {@link List} of {@link ParsedLinkResponse} parsed from the CSS
     * {@link PageContent}
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
                //LOG.log(Level.INFO, "Link found in CSS: {0}", m.group(1));
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
     * Formats {@link String} relative URL parsed from the CSS
     * {@link PageContent} to its absolute form based on base {@link URL}.
     *
     * @param url {@link String} relative URL parsed from the CSS
     * {@link PageContent}
     * @param baseURL base {@link URL} of the CSS file
     * @return absolute {@link LinkURL} formated from relative and base
     * {@link URL}
     * @throws MalformedURLException If formating fails.
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
