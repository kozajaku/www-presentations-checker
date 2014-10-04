package org.presentation.parser.impl;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import org.presentation.model.PageContent;
import org.presentation.parser.CSSParserService;
import org.presentation.parser.ParsedLinkResponse;

/**
 * @author Adam Kugler
 * @version 1.0
 */
@Dependent
public class CSSParserServiceDefault implements CSSParserService {

	/**
	 * 
	 * @param sourceCode
	 */
        @Override
	public List<ParsedLinkResponse> parseLinks(PageContent sourceCode){
		return new ArrayList<>();
	}

//	/**
//	 * 
//	 * @param sourceCode
//	 */
//	public CSSSelectorTree parseWholeCSSPage(PageContent sourceCode){
//		return null;
//	}

}