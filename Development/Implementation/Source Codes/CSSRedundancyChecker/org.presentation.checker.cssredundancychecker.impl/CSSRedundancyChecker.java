package CSSRedundancyChecker.org.presentation.checker.cssredundancychecker.impl;
import Model.org.presentation.checker.model.logging.MessageLogger;
import WholePresentationController.org.presentation.checker.wholepresentationcontroller.impl.WholePresentationChecker;

/**
 * @author Adam
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public class CSSRedundancyChecker implements WholePresentationChecker {

	private CSSCodeRC CSSSet;
	private HTMLCodeRC HTMLSet;
	private MessageLogger logger;
	private TraversalGraph pageGraph;

	public CSSRedundancyChecker(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Returns if linking was succesfull.
	 */
	private boolean linkHTMLCSS(){
		return false;
	}

	/**
	 * 
	 * @param container
	 */
	public void offerMsgLoggerContainer(MessageLoggerContainer container){

	}

	public void stopChecking(){

	}

	public boolean needParsedCode(){
		return false;
	}

	public String getID(){
		return "";
	}

	/**
	 * 
	 * @param html
	 * @param css
	 * @param graph
	 */
	public void checkPresentation(HTML html, CSS css, TraversalGraph graph){

	}

}