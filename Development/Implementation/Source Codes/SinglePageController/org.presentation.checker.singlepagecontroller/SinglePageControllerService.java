package SinglePageController.org.presentation.checker.singlepagecontroller;
import Model.org.presentation.checker.model.PageContent;
import Model.org.presentation.checker.model.LinkURL;
import Model.org.presentation.checker.model.logging.MessageProducer;
import Utils.org.presentation.checker.utils.Stoppable;
import Utils.org.presentation.checker.utils.Option;

/**
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public interface SinglePageControllerService extends MessageProducer, Stoppable, Option {

	/**
	 * 
	 * @param url
	 * @param text
	 */
	public abstract void checkPage(LinkURL url, PageContent text);

}