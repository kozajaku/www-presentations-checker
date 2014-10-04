package org.presentation.parser.impl;
import java.util.List;
import org.presentation.model.PageContent;
import org.presentation.parser.HTMLParserService;
import org.presentation.parser.ParsedLinkResponse;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public class HTMLParserServiceDefault implements HTMLParserService {

	/**
	 * 
	 * @param sourceCode
	 */
        @Override
	public List<ParsedLinkResponse> parseLinks(PageContent sourceCode){
		return null;
	}

//	/**
//	 * 
//	 * @param sourceCode
//	 */
//	public HTMLTagTree parseWholeHTMLPage(PageContent sourceCode){
//		return null;
//	}

}