package org.presentation.parser;

import java.util.List;
import org.presentation.model.PageContent;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public interface HTMLParserService {

	/**
	 * 
	 * @param sourceCode
         * @return 
	 */
	public abstract List<ParsedLinkResponse> parseLinks(PageContent sourceCode);

	/**
	 * 
	 * @param sourceCode
	 */
	//public abstract HTMLTagTree parseWholeHTMLPage(PageContent sourceCode);

}