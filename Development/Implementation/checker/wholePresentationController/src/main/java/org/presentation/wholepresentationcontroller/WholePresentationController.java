package org.presentation.wholepresentationcontroller;

import java.util.List;
import javax.ejb.Lock;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MessageProducer;
import org.presentation.utils.OptionContainer;
import org.presentation.utils.Stoppable;


/**
 * @author Adam
 * @version 1.0
 * @created 25-10-2014 22:43:27
 */
public class WholePresentationController implements Stoppable, MessageProducer {

	/**
	 * @author radio.koza
	 * @version 1.0
	 * @created 25-10-2014 22:43:27
	 */
	public class AsyncAddPage implements Runnable {

		private PageContent pageContent;
		private LinkURL linkURL;
		private ContentType contentType;

		public AsyncAddPage(){

		}

		public void finalize() throws Throwable {
			super.finalize();
		}

		/**
		 * 
		 * @param pageContent
		 * @param linkURL
		 * @param contentType
		 */
		public void AsyncAddPage(PageContent pageContent, LinkURL linkURL, ContentType contentType){

		}

		public void run(){

		}

	}

	private TraversalGraph pageGraph;
	private Instance<WholePresentationChecker> wholePresentationCheckersPrototype;
	private List<WholePresentationChecker> wholePresentationCheckers;
	private Lock lockCSS;
	private Lock lockHTML;
	private ManagedExecutorService mes;
	private Lock lockDone;

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

	/**
	 * 
	 * @param optionContainer
	 */
	public void initializeControllers(OptionContainer optionContainer){

	}

	public void checkPresentation(){

	}

	public void waitForDone(){

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