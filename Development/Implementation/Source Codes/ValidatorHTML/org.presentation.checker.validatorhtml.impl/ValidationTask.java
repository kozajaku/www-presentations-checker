package ValidatorHTML.org.presentation.checker.validatorhtml.impl;
import Model.org.presentation.checker.model.LinkURL;
import Model.org.presentation.checker.model.PageContent;
import Model.org.presentation.checker.model.logging.MessageLogger;

/**
 * Use
 * @author Tunik
 * @version 1.0
 * @created 04-5-2014 20:56:26
 */
public class ValidationTask implements Runnable {

	private LinkURL url;
	private PageContent content;
	private MessageLogger logger;

	public ValidationTask(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param url
	 * @param content
	 * @param logger
	 */
	public ValidationTask(LinkURL url, PageContent content, MessageLogger logger){

	}

	/**
	 * Implementation for interface Runnable.
	 */
	public void run(){

	}

}