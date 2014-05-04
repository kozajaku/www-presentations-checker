package Kernel.org.presentation.checker.kernel;
import WebCrawler.org.presentation.checker.crawler.CrawlingState;
import Model.org.presentation.checker.model.graph.TraversalGraph;
import Model.org.presentation.checker.model.logging.MsgReport;
import Utils.org.presentation.checker.utils.Stoppable;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:25
 */
public interface KernelService extends Stoppable {

	/**
	 * 
	 * @param startingLink
	 */
	public abstract void startValidation(LinkURL startingLink);

	/**
	 * 
	 * @param startingLink
	 * @param allowedDomains
	 * @param checkingOptions
	 * @param requestTimeout
	 */
	public abstract void startValidation(LinkURL startingLink, List <Domain> allowedDomains, OptionContainer checkingOptions, int requestTimeout);

	public abstract CrawlingState getCrawlingState();

	/**
	 * 
	 * @param observer
	 */
	public abstract void registerControllingObserver(ControllingObserver observer);

	public TraversalGraph getTraversalGraph();

	public MsgReport generateMsgReport();

}