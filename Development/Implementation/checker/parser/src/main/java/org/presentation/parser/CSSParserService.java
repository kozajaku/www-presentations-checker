package org.presentation.parser;

import java.util.List;
import org.presentation.model.PageContent;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public interface CSSParserService {

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
	//public abstract CSSSelectorTree parseWholeCSSPage(PageContent sourceCode);

}