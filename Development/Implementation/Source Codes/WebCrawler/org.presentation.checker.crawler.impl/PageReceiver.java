package WebCrawler.org.presentation.checker.crawler.impl;
import Model.org.presentation.checker.model.logging.MessageProducer;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class PageReceiver implements MessageProducer {

	public PageReceiver(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param link
	 */
	public abstract ReceiverResponse getPage(LinkURL link);

	/**
	 * 
	 * @param container
	 */
	public void offerMsgLoggerContainer(MessageLoggerContainer container){

	}

}