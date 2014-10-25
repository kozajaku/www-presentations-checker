package org.presentation.parser;

import java.util.List;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * <p>
 * CSSParserService interface.</p>
 *
 * @author Adam Kugler
 * @version 1.0
 */
public interface CSSParserService {

    /**
     * Parses links from CSS page. Supported links are specified in
     * LinkSourceType.
     *
     * @param sourceCode code of CSS page
     * @param baseURL URL of location of this CSS page
     * @return links parsed from CSS page
     */
    public List<ParsedLinkResponse> parseLinks(PageContent sourceCode, LinkURL baseURL);

    /**
     *
     * @param sourceCode
     */
	//public CSSSelectorTree parseWholeCSSPage(PageContent sourceCode);
}
