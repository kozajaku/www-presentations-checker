package SinglePageController.org.presentation.checker.singlepagecontroller;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:26
 */
public class SPCServiceProviderFactory {

	/**
	 * Instance of this singleton object.
	 */
	private static SPCServiceProviderFactory instance;



	public void finalize() throws Throwable {

	}

	/**
	 * Public constructor is forbidden.
	 */
	private SPCServiceProviderFactory(){

	}

	/**
	 * Getter for singleton instance for this Factory class.
	 */
	public static SPCServiceProviderFactory getInstance(){
		return null;
	}

	public Collection<SinglePageControllerService> getSPSServiceImplementations(){
		return null;
	}

}