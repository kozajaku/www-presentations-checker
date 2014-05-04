package Parser.org.presentation.checker.parser.impl;
import Parser.org.presentation.checker.parser.CSSParserService;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public class CSSParserServiceDefault implements CSSParserService {

	public CSSParserServiceDefault(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param sourceCode
	 */
	public List<ParsedLinkResponse> parseLinks(PageContent sourceCode){
		return null;
	}

	/**
	 * 
	 * @param sourceCode
	 */
	public CSSSelectorTree parseWholeCSSPage(PageContent sourceCode){
		return null;
	}

}