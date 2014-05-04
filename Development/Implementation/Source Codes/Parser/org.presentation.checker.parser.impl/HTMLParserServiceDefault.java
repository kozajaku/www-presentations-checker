package Parser.org.presentation.checker.parser.impl;
import Parser.org.presentation.checker.parser.HTMLParserService;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:24
 */
public class HTMLParserServiceDefault implements HTMLParserService {

	public HTMLParserServiceDefault(){

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
	public HTMLTagTree parseWholeHTMLPage(PageContent sourceCode){
		return null;
	}

}