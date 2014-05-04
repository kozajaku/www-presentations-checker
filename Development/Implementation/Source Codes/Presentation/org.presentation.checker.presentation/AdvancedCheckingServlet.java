package Presentation.org.presentation.checker.presentation;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public class AdvancedCheckingServlet extends WebServlet {

	private LinkURL startingLink;
	private Domain allowedDomainsList;
	private OptionService optionsList;

	public AdvancedCheckingServlet(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param startingLink
	 * @param allowedDomains
	 * @param chosenOptions
	 * @param requestTimeout
	 */
	public void startValidation(LinkURL startingLink, List <Domain> allowedDomains, OptionContainer chosenOptions, int requestTimeout){

	}

}