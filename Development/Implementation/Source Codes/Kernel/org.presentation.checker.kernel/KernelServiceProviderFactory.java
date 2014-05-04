package Kernel.org.presentation.checker.kernel;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class KernelServiceProviderFactory {

	/**
	 * Singleton instance.
	 */
	private static KernelServiceProviderFactory instance;



	public void finalize() throws Throwable {

	}

	/**
	 * Public constructor is forbidden in this singleton class.
	 */
	private KernelServiceProviderFactory(){

	}

	public static KernelServiceProviderFactory getInstance(){
		return null;
	}

	public KernelService getKernelServiceImpl(){
		return null;
	}

}