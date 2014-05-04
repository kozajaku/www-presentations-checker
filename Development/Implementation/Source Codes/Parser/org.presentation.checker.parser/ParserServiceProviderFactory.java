package Parser.org.presentation.checker.parser;

/**
 * Singleton class for receaving instance of ParserService interface
 * implementation class.
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class ParserServiceProviderFactory {

	/**
	 * Holder for singleton instance for ParserServiceProviderFactory class.
	 */
	private static ParserServiceProviderFactory instance;



	public void finalize() throws Throwable {

	}

	/**
	 * The public constructor is forbidden in singleton pattern.
	 */
	private ParserServiceProviderFactory(){

	}

	/**
	 * Returns singleton instance of class ParserServiceProviderFactory.
	 */
	public static ParserServiceProviderFactory getInstance(){
		return null;
	}

	/**
	 * Returns concrete CSSParserService implementation. If no service provider is
	 * registered into OSGi bundle, the default implementation is returned.
	 */
	public CSSParserService getCSSParserServiceImpl(){
		return null;
	}

	/**
	 * Returns concrete HTMLParserService implementation. If no service provider is
	 * registered into OSGi bundle, the default implementation is returned.
	 */
	public HTMLParserService getHTMLParserServiceImpl(){
		return null;
	}

}