package org.presentation.parser;

import java.util.List;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public interface HTMLParserService {

	/**
	 * Parses link from HTML page. Supported links are specified in LinkSourceType.
	 * @param sourceCode code of HMTL page
         * @param baseURL URL of location of this HTML page
         * @return links parsed from HTML page
	 */
	List<ParsedLinkResponse> parseLinks(PageContent sourceCode, LinkURL baseURL);

	/**
	 * 
	 * @param sourceCode
	 */
	//HTMLTagTree parseWholeHTMLPage(PageContent sourceCode);

}