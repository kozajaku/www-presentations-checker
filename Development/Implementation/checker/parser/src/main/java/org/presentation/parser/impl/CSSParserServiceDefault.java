package org.presentation.parser.impl;
import java.util.List;
import org.presentation.model.PageContent;
import org.presentation.parser.CSSParserService;
import org.presentation.parser.ParsedLinkResponse;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public class CSSParserServiceDefault implements CSSParserService {

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
//	public CSSSelectorTree parseWholeCSSPage(PageContent sourceCode){
//		return null;
//	}

}