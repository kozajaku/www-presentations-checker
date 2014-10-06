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
	 * 
	 * @param sourceCode
         * @param baseURL
         * @return 
	 */
	List<ParsedLinkResponse> parseLinks(PageContent sourceCode, LinkURL baseURL);

	/**
	 * 
	 * @param sourceCode
	 */
	//HTMLTagTree parseWholeHTMLPage(PageContent sourceCode);

}