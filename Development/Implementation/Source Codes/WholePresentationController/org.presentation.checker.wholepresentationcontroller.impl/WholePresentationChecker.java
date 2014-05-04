package WholePresentationController.org.presentation.checker.wholepresentationcontroller.impl;
import Utils.org.presentation.checker.utils.Stoppable;
import Utils.org.presentation.checker.utils.Option;
import Model.org.presentation.checker.model.logging.MessageProducer;

/**
 * @author Adam
 * @version 1.0
 * @created 04-5-2014 20:56:26
 */
public interface WholePresentationChecker extends Stoppable, Option, MessageProducer {

	/**
	 * 
	 * @param html
	 * @param css
	 * @param graph
	 */
	public abstract void checkPresentation(HTML html, CSS css, TraversalGraph graph);

	public boolean needParsedCode();

}