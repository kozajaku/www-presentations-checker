package Parser.org.presentation.checker.parser;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public interface CSSParserService {

	/**
	 * 
	 * @param sourceCode
	 */
	public abstract List<ParsedLinkResponse> parseLinks(PageContent sourceCode);

	/**
	 * 
	 * @param sourceCode
	 */
	public abstract CSSSelectorTree parseWholeCSSPage(PageContent sourceCode);

}