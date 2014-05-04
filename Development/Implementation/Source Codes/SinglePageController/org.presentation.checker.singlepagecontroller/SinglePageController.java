package SinglePageController.org.presentation.checker.singlepagecontroller;
import Utils.org.presentation.checker.utils.OptionContainer;
import Model.org.presentation.checker.model.PageContent;
import Model.org.presentation.checker.model.LinkURL;
import Utils.org.presentation.checker.utils.Stoppable;
import Model.org.presentation.checker.model.logging.MessageProducer;

/**
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class SinglePageController implements Stoppable, MessageProducer {

	private OptionContainer chosenOptions;
	private SinglePageControllerService singlePageCheckersCollection;



	public void finalize() throws Throwable {
		super.finalize();
	}

	public SinglePageController(){

	}

	/**
	 * 
	 * @param chosenOptions
	 */
	public SinglePageController(OptionContainer chosenOptions){

	}

	/**
	 * 
	 * @param contentType
	 * @param url
	 * @param text
	 */
	public void checkPage(ContentType contentType, LinkURL url, PageContent text){

	}

	/**
	 * 
	 * @param container
	 */
	public void offerMsgLoggerContainer(MessageLoggerContainer container){

	}

	public void stopChecking(){

	}

}