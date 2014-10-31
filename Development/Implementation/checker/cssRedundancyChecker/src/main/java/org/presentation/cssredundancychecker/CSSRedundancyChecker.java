package org.presentation.cssredundancychecker;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.Declaration;
import cz.vutbr.web.css.NodeData;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.fit.cssbox.css.DOMAnalyzer;
import org.presentation.cssredundancychecker.model.CRCCssCode;
import org.presentation.cssredundancychecker.model.CRCHtmlCode;
import org.presentation.cssredundancychecker.model.CSSRule;
import org.presentation.cssredundancychecker.model.CSSRuleSet;
import org.presentation.cssredundancychecker.model.CSSRuleUsage;
import org.presentation.cssredundancychecker.model.DeclarationPosition;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.ErrorMsg;
import org.presentation.model.logging.InfoMsg;
import org.presentation.model.logging.Message;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MsgLocation;
import org.presentation.model.logging.WarningMsg;
import org.presentation.parser.CSSCode;
import org.presentation.parser.HTMLCode;
import org.presentation.parser.helper.DOMBuilder;
import org.presentation.wholepresentationcontroller.WholePresentationChecker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Adam
 */
public class CSSRedundancyChecker implements WholePresentationChecker {

    protected Map<LinkURL, List<CRCHtmlCode>> stylesheetDependencies;
    protected Map<LinkURL, CRCCssCode> stylesheetsByURL;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    private MessageLogger messageLogger;
    private boolean stopped;

    public CSSRedundancyChecker() {
        this.stylesheetDependencies = new HashMap<>();
    }

    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer) {
        messageLogger = messageLoggerContainer.createLogger("CSS redundancy checker");
    }

    @Override
    public String getID() {
        return "CSS redundancy checker";
    }

    @Override
    public void stopChecking() {
        LOG.info("stop checking");
        stopped = true;
    }

    @Override 
    public void addPage(ContentType contentType, LinkURL linkURL, PageContent pageContent, CSSCode cssCode) {
        List<CRCHtmlCode> documentsProcessed = new ArrayList<>();
        
	CRCCssCode crcCssCode;
	try {
	    crcCssCode = new CRCCssCode(cssCode);
	    this.stylesheetsByURL.put(linkURL, crcCssCode);

	    if (this.stylesheetDependencies.containsKey(linkURL)) {
		for (CRCHtmlCode waitingDocument : ((List<CRCHtmlCode>) this.stylesheetDependencies.get(linkURL))) {
		    if (this.areAllStylesheetsForDocumentAvailable(waitingDocument)) {
			processSinglePage(waitingDocument);
			documentsProcessed.add(waitingDocument);
		    }
		}
	    }
	    this.stylesheetDependencies.remove(linkURL);
	    this.freeDocumentsFromPrison(documentsProcessed);
	} catch (CSSException ex) {
	    ErrorMsg errMsg = new ErrorMsg();
	    errMsg.setMessage("CSS document cannot be parsed!");
	    errMsg.setPage(linkURL);
	    this.messageLogger.addMessage(errMsg);
	}	    
    }

    @Override
    public void addPage(ContentType contentType, LinkURL linkURL, PageContent pageContent, HTMLCode htmlCode) {
        CRCHtmlCode document = new CRCHtmlCode(htmlCode);
        if (this.areAllStylesheetsForDocumentAvailable(document)) {
            this.processSinglePage(document);
        } else {
            List<LinkURL> missingStylesheets = this.getMissingStylesheetsForDocument(document);
            for (LinkURL missingStylesheet : missingStylesheets) {
                if (!this.stylesheetDependencies.containsKey(missingStylesheet)) {
                    this.stylesheetDependencies.put(missingStylesheet, new ArrayList<CRCHtmlCode>());
                }
                ((List<CRCHtmlCode>) this.stylesheetDependencies.get(missingStylesheet)).add(document);
            }
        }
    }

    /**
     * This method frees resources allocated by HTML document caching.     
     * @param documents list of documents to be removed from the cache list
     */
    private void freeDocumentsFromPrison(List<CRCHtmlCode> documents) {
        for (List<CRCHtmlCode> stylesheetDepencyList : this.stylesheetDependencies.values()) {
            stylesheetDepencyList.removeAll(documents);
        }
    }

    /**
     * Return URLs of all stylesheet documents that are missing for the given document.
     * This method compares the list of stylesheet documents that have been already
     * supplied by the crawler and the list of stylesheets that are required by 
     * the document itself (provided by CRCHtmlCode).
     * @param document
     * @return 
     */
    private List<LinkURL> getMissingStylesheetsForDocument(CRCHtmlCode document) {
        List<LinkURL> stylesheetsMissing = new ArrayList<>();
	for(LinkURL stylesheetRequired : document.getStylesheetFilesRequired()) {
	    if(!this.stylesheetsByURL.containsKey(stylesheetRequired)) {
		stylesheetsMissing.add(stylesheetRequired);
	    }
	}
	return stylesheetsMissing;
    }

    /**
     * Checks if all the stylesheet files required by the particular document are 
     * available (have been provided by WebCrawler)
     * @param document
     * @return 
     */
    private boolean areAllStylesheetsForDocumentAvailable(CRCHtmlCode document) {
        return this.getMissingStylesheetsForDocument(document).isEmpty();
    }

    /**
     * This metod is called to process a single HTML page. It must be called AFTER
     * all required stylesheets are loaded. It prepares the document to be analyzed 
     * by DOMAnalyzer. Then it calls doTheCheck method that actually walks through 
     * the HTML DOM and evaluates HTML elements;
     * @param document 
     */
    private void processSinglePage(CRCHtmlCode document) {
	Document parsedDocument = DOMBuilder.jsoup2DOM(document.getHtmlCode().getParsedHTML());
	
	// remove stylesheet from the dom
	NodeList linkElements = parsedDocument.getElementsByTagName("link");
	for(int i = 0; i < linkElements.getLength(); i++) {
	    if (linkElements.item(i).getNodeType() == Node.ELEMENT_NODE) {
		Element linkElement = ((Element) linkElements.item(i));
		if( (linkElement.hasAttribute("rel") && linkElement.getAttribute("rel").toLowerCase().equals("stylesheet")) ||
		    (linkElement.hasAttribute("type") && linkElement.getAttribute("type").toLowerCase().equals("text/css"))) {		    
			linkElement.getParentNode().removeChild(linkElement);
		}
	    }
	}
	
	// add custom stylesheets
	DOMAnalyzer domAnalyzer = new DOMAnalyzer(parsedDocument);
	
	for(LinkURL stylesheetRequiredUrl : document.getStylesheetFilesRequired()) {
	    if(this.stylesheetsByURL.containsKey(stylesheetRequiredUrl)){
		try {
		    domAnalyzer.addStyleSheet(new URL(stylesheetRequiredUrl.getUrl()), this.stylesheetsByURL.get(stylesheetRequiredUrl).getCssCode().getCodeCSS().getContent(), DOMAnalyzer.Origin.AUTHOR);
		} catch (MalformedURLException ex) {
		    // that cannot happen (valid url set)
		}
	    }
	}
	domAnalyzer.getStyleSheets();	
	
	doTheCheck(parsedDocument, domAnalyzer, document);	
    }
    
    /**
     * Process the element's rules and add an usage for each of the rules.
     * @param element Element to be processed
     * @param hasTextContent True if the element contains any text itself (contains at least one Text Node)
     * @param domAnalyzer Dom analyzer for the given document
     * @param ruleUsage Usage to be added to the usage list for each rule
     */
    protected void applyDiscoveredRules(Element element, boolean hasTextContent, DOMAnalyzer domAnalyzer, CSSRuleUsage ruleUsage){
	NodeData nodeData;
	nodeData = domAnalyzer.getElementStyleInherited((Element) element);		
	if(nodeData != null) {		
	    Collection<String> cssPropertyNames;
	    cssPropertyNames = nodeData.getPropertyNames();

	    // load properties
	    for(String cssPropertyName : cssPropertyNames) {

		// check the property is not inheritable (is applied without text contnet) or the element contains text
		if(!nodeData.getProperty(cssPropertyName).inherited() || hasTextContent) {

		    // load declaration sources
		    Declaration sourceDeclaration = nodeData.getSourceDeclaration(cssPropertyName, true);
		    if(sourceDeclaration != null) {
			Declaration.Source source = sourceDeclaration.getSource();
			if(source != null) {

			    // CRCCssCode resolve by url
			    LinkURL sourceUrl = new LinkURL(source.getUrl().toString());
			    if(this.stylesheetsByURL.containsKey(sourceUrl)) {
				CRCCssCode cssDocument = this.stylesheetsByURL.get(sourceUrl);

				// get particular CSS rule and add the usage
				CSSRule cssRule = cssDocument.getCssRuleByPosition(new DeclarationPosition(source.getLine(), source.getPosition()));
				if(cssRule != null && ruleUsage != null) {
				    cssRule.addRuleUsage(ruleUsage);					
				}
			    }
			}
		    }
		}
		
	    }
	}
	    	
    }
    
    /**
     * This function iterates over the HTML DOM tree and call applyDiscoveredRules on each Element Node
     * @param parsedDocument Parsed document
     * @param domAnalyzer Dom analyzer for the given document
     * @param document Html document
     */
    protected void doTheCheck (Document parsedDocument, DOMAnalyzer domAnalyzer, CRCHtmlCode document) {
	// walk through all elements (DFS)
	Node curNode;
	
	curNode = parsedDocument.getDocumentElement();
	
	while(curNode != null) {
	    if(curNode.hasChildNodes()) {
		Node nextNode = curNode.getFirstChild();
		
		// text child identified, we mark his parent as "has_text_content"
		if(nextNode.getNodeType() == Node.TEXT_NODE) {
		    if(curNode.getNodeType() == Node.ELEMENT_NODE) {
			((Element)curNode).setAttribute("____CSSRC____has_test_content", "1");
		    }		    
		    curNode.removeChild(nextNode);  // we don't care about the text element any more
		} else if(nextNode.getNodeType() == Node.ELEMENT_NODE) {
		    // other than text element discovered, let's go deep
		    curNode = nextNode;
		} else {
		    // do nothing
		    curNode.removeChild(nextNode);
		}
	    } else {
		// this node is already empty, youpee!
		Node nextNode = curNode.getParentNode();
		if(curNode.getParentNode() != null) curNode.getParentNode().removeChild(curNode);
		if(curNode.getNodeType() == Node.ELEMENT_NODE) applyDiscoveredRules((Element) curNode, ((Element) curNode).hasAttribute("____CSSRC____has_test_content"), domAnalyzer, new CSSRuleUsage(document.getHtmlCode().getLinkHTML()) );
		curNode = nextNode;
	    }
	}
	
	    
	  /*  
	for (int i = 0; i < allElements.getLength(); i++) {
	    Node curNode = allElements.item(i);
	    if (curNode.getNodeType() == Node.ELEMENT_NODE) { 		
		NodeData nodeData;
		nodeData = domAnalyzer.getElementStyleInherited((Element) curNode);		
		if(nodeData != null) {		
		    Collection<String> cssPropertyNames;
		    cssPropertyNames = nodeData.getPropertyNames();
		
		    // load properties
		    for(String cssPropertyName : cssPropertyNames) {
			
			// load declaration sources
			Declaration sourceDeclaration = nodeData.getSourceDeclaration(cssPropertyName, true);
			if(sourceDeclaration != null) {
			    Declaration.Source source = sourceDeclaration.getSource();
			    if(source != null){
				
				// CRCCssCode resolve by url
				LinkURL sourceUrl = new LinkURL(source.getUrl().toString());
				if(this.stylesheetsByURL.containsKey(sourceUrl)) {
				    CRCCssCode cssDocument = this.stylesheetsByURL.get(sourceUrl);
				    
				    // get particular CSS rule and add the usage
				    CSSRule cssRule = cssDocument.getCssRuleByPosition(new DeclarationPosition(source.getLine(), source.getPosition()));
				    if(cssRule != null) {
					cssRule.addRuleUsage(new CSSRuleUsage(
					    document.getHtmlCode().getLinkHTML()
					));					
				    }
				}
			    }
			}
		    }
		}
	    }
	}	
		  */
    }
      

    /**
     * This function is called when the checkup is finished. It sums up the results, generates
     * result messages and also process the rest of the documents (those with missing stylesheets)
     * @param traversalGraph unused
     */
    @Override
    public void finalizeCheckup(TraversalGraph traversalGraph) {
        // if some files are still waiting for some missing stylesheets, let's process them at the end of checkup
        List<CRCHtmlCode> documentsProcessed = new ArrayList<>();
        for (List<CRCHtmlCode> documentList : this.stylesheetDependencies.values()) {
	    for (CRCHtmlCode document : documentList) {
		processSinglePage(document);
		documentsProcessed.add(document);
	    }
	}
	this.freeDocumentsFromPrison(documentsProcessed);
	    
	// todo generate messages
	for(CRCCssCode cssDocument : this.stylesheetsByURL.values()) {
	    List<CSSRuleSet> cssRuleBlocks = cssDocument.getCssRuleBlocks();
	    for(CSSRuleSet cssRuleBlock : cssRuleBlocks)  {
		
		MsgLocation blockMsgLocation = null;
		// because block can't be aimed by row:col, let's try to find the first rule at least..
		if(!cssRuleBlock.getCssRules().isEmpty()){
		    CSSRule firstRule = cssRuleBlock.getCssRules().get(0);
		    if(firstRule != null) blockMsgLocation = new MsgLocation( firstRule.getDeclarationPosition().getLine(), firstRule.getDeclarationPosition().getCol() );			
		}
		
		if(cssRuleBlock.isRedundant()) {
		    
		    this.messageLogger.addMessage(this.fillMessage( new WarningMsg(), cssDocument.getCssCode().getLinkCSS(),
			    "Whole rule block \"" + cssRuleBlock.toString() + "\" is redundant"
			    , blockMsgLocation, 10));
		} else {
		    
		    // not whole CSS block is redundant, let's list only redundant css properties
		    for(CSSRule cssRule : cssRuleBlock.getCssRules()) {
			if(cssRule.isRedundant()) {
			    this.messageLogger.addMessage(this.fillMessage( new WarningMsg(), cssDocument.getCssCode().getLinkCSS(),
				    "Redundant rule \"" + cssRule.getName() + "\" in \"" + cssRuleBlock.toString() + "\""
				    , new MsgLocation(cssRule.getDeclarationPosition().getLine(), cssRule.getDeclarationPosition().getCol()), 10));			    
			}
		    }
		    
		    // not whole CSS block is redundant, let's also generate the utilisation message
		    List<Integer> usageCounts = new ArrayList<>();
		    for(CSSRule cssRule : cssRuleBlock.getCssRules()) {
			if(!cssRule.isRedundant()) {
			    usageCounts.add(cssRule.getCssRuleUsages().size());
			}
		    }
		    this.messageLogger.addMessage(this.fillMessage(new InfoMsg(), cssDocument.getCssCode().getLinkCSS(), "Rule block \"" + cssRuleBlock.toString() + "\" has " + Collections.max(usageCounts) + " usages", blockMsgLocation, 0));
		}
	    }
	}
    }
    
    protected Message fillMessage(Message message, LinkURL url, String text, MsgLocation location, int priorityBoost) {
	message.setPage(url);
	message.setMessage(text);
	message.setMsgLocation(location);
	message.setPriorityBoost(priorityBoost);
	return message;
    }
    
}
