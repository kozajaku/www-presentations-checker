package Kernel.org.presentation.checker.kernel.impl;
import Model.org.presentation.checker.model.logging.MessageLoggerContainer;
import SinglePageController.org.presentation.checker.singlepagecontroller.SinglePageController;
import WebCrawler.org.presentation.checker.crawler.CrawlerService;
import Kernel.org.presentation.checker.kernel.ControllingObserver;
import Kernel.org.presentation.checker.kernel.KernelService;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public class KernelServiceDefaultImpl implements KernelService {

	private MessageLoggerContainer msgLoggerContainer;
	private PageCrawlingObserver crawlingObserver;
	private WholePresentationController wholePresentationController;
	private SinglePageController singlePageController;
	private CrawlerService crawler;
	private ControllingObserver observer;

	public KernelServiceDefaultImpl(){

	}

	public void finalize() throws Throwable {

	}

	public void stopChecking(){

	}

	/**
	 * 
	 * @param startingLink
	 */
	public void startValidation(LinkURL startingLink){

	}

	/**
	 * 
	 * @param startingLink
	 * @param allowedDomains
	 * @param checkingOptions
	 * @param requestTimeout
	 */
	public void startValidation(LinkURL startingLink, List <Domain> allowedDomains, OptionContainer checkingOptions, int requestTimeout){

	}

	public CrawlingState getCrawlingState(){
		return null;
	}

	/**
	 * 
	 * @param observer
	 */
	public void registerControllingObserver(ControllingObserver observer){

	}

	public TraversalGraph getTraversalGraph(){
		return null;
	}

	public MsgReport generateMsgReport(){
		return null;
	}

}