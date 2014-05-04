package WholePresentationController.org.presentation.checker.wholepresentationcontroller.impl;
import Parser.org.presentation.checker.parser.CSS;
import Parser.org.presentation.checker.parser.HTML;
import Utils.org.presentation.checker.utils.OptionContainer;
import Model.org.presentation.checker.model.graph.TraversalGraph;
import Model.org.presentation.checker.model.LinkURL;
import Model.org.presentation.checker.model.PageContent;
import Utils.org.presentation.checker.utils.Stoppable;
import Model.org.presentation.checker.model.logging.MessageProducer;

/**
 * @author Adam
 * @version 1.0
 * @created 04-5-2014 20:56:26
 */
public class WholePresentationController implements Stoppable, MessageProducer {

	private CSS CSSCodesList;
	private HTML HTMLcodesList;
	private OptionContainer chosenOptions;
	private TraversalGraph pageGraph;
	private WholePresentationChecker WholePresentationCheckersList;

	public WholePresentationController(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param graph
	 */
	public void addGraph(TraversalGraph graph){

	}

	/**
	 * 
	 * @param code
	 * @param link
	 * @param contentType
	 */
	public void addPage(PageContent code, LinkURL link, ContentType contentType){

	}

	public void checkPresentation(){

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