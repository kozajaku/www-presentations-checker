package org.presentation.cssredundancychecker;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.Declaration;
import cz.vutbr.web.css.NodeData;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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

    Map<LinkURL, List<CRCHtmlCode>> stylesheetDependencies;
    Map<LinkURL, CRCCssCode> stylesheetsByURL;
    List<CRCCssCode> cssCodes;
    List<CRCHtmlCode> htmlCodes;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    MessageLogger messageLogger;
    boolean stopped;

    public CSSRedundancyChecker() {
        this.stylesheetDependencies = new HashMap<>();
        this.cssCodes = new ArrayList<>();
        this.htmlCodes = new ArrayList<>();
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
	    this.cssCodes.add(crcCssCode);
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
            this.htmlCodes.add(document);
        }
    }

    private void freeDocumentsFromPrison(List<CRCHtmlCode> documents) {
        this.htmlCodes.removeAll(documents);
        for (List<CRCHtmlCode> stylesheetDepencyList : this.stylesheetDependencies.values()) {
            stylesheetDepencyList.removeAll(documents);
        }
    }

    private List<LinkURL> getMissingStylesheetsForDocument(CRCHtmlCode document) {
        return new ArrayList<>();
    }

    private boolean areAllStylesheetsForDocumentAvailable(CRCHtmlCode document) {
        return this.getMissingStylesheetsForDocument(document).isEmpty();
    }

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
	
	// walk through all elements
	NodeList allElements = parsedDocument.getElementsByTagName("*");
	NodeData nodeData;
	Collection<String> cssPropertyNames;
	
	for (int i = 0; i < allElements.getLength(); i++) {
	    Node curNode = allElements.item(i);
	    if (curNode.getNodeType() == Node.ELEMENT_NODE) { 		
		nodeData = domAnalyzer.getElementStyleInherited((Element) curNode);		
		if(nodeData != null) {		
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
				    
				    // get concrete CSS rule and add the usage
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
	
    }

    @Override
    public void finalizeCheckup(TraversalGraph traversalGraph) {
        // if some files are still waiting for some missing stylesheets, let's process them at the end of checkup
        List<CRCHtmlCode> documentsProcessed = new ArrayList<>();
        for (CRCHtmlCode document : htmlCodes) {
            processSinglePage(document);
            documentsProcessed.add(document);
        }
        this.freeDocumentsFromPrison(documentsProcessed);

        // todo generate messages
	for(CRCCssCode cssDocument : this.cssCodes) {
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
