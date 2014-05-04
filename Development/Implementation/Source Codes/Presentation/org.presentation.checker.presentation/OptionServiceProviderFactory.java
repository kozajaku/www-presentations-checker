package Presentation.org.presentation.checker.presentation;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class OptionServiceProviderFactory {

	/**
	 * Singleton instance holder for this class.
	 */
	private static OptionServiceProviderFactory instance;



	public void finalize() throws Throwable {

	}

	/**
	 * Public constructor is forbidden.
	 */
	private OptionServiceProviderFactory(){

	}

	/**
	 * Returns singleton instance holder.
	 */
	public static OptionServiceProviderFactory getInstance(){
		return null;
	}

	public List <OptionService> getAllAvailableOptions(){
		return null;
	}

}