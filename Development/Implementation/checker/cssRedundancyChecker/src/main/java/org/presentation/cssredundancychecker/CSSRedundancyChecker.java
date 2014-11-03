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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.cssredundancychecker.cssboxHelper.DOMAnalyzerEnhanced;
import org.presentation.cssredundancychecker.model.CRCCssCode;
import org.presentation.cssredundancychecker.model.CRCHtmlCode;
import org.presentation.cssredundancychecker.model.CSSRule;
import org.presentation.cssredundancychecker.model.CSSRuleSet;
import org.presentation.cssredundancychecker.model.CSSRuleUsage;
import org.presentation.cssredundancychecker.model.DeclarationPosition;
import org.presentation.model.LinkURL;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.ErrorMsg;
import org.presentation.model.logging.InfoMsg;
import org.presentation.model.logging.Message;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MsgLocation;
import org.presentation.model.logging.WarningMsg;
import org.presentation.parser.AbstractCode;
import org.presentation.parser.CSSCode;
import org.presentation.parser.CodeType;
import org.presentation.parser.HTMLCode;
import org.presentation.parser.helper.DOMBuilder;
import org.presentation.wholepresentationcontroller.WholePresentationChecker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Adam
 */
@Dependent
public class CSSRedundancyChecker implements WholePresentationChecker {

    static final String SERVICE_NAME = "CSS redundancy checker";

    protected Map<LinkURL, List<CRCHtmlCode>> stylesheetDependencies;
    protected Map<LinkURL, CRCCssCode> stylesheetsByURL;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    private MessageLogger messageLogger;
    private boolean stopped;

    public CSSRedundancyChecker() {
        this.stylesheetDependencies = new HashMap<>();
        this.stylesheetsByURL = new HashMap<>();

        // todo delete
        if (this.LOG == null) {
            this.LOG = Logger.getLogger(CSSRedundancyChecker.class.getName());
        }
    }

    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer) {
        messageLogger = messageLoggerContainer.createLogger("CSS redundancy checker");
    }

    @Override
    public String getID() {
        return SERVICE_NAME;
    }

    @Override
    public void stopChecking() {
        LOG.info("stop checking");
        stopped = true;
    }

    @Override
    public void addPage(AbstractCode code) {
        if (code.getType() == CodeType.HTML_CODE) {
            addHTMLPage((HTMLCode) code);
        } else if (code.getType() == CodeType.CSS_CODE) {
            addCSSPage((CSSCode) code);
        }
    }

    public void addCSSPage(CSSCode cssCode) {
        List<CRCHtmlCode> documentsProcessed = new ArrayList<>();
        LOG.log(Level.INFO, "Adding CSS page {0}", cssCode.getLink().getUrl());

        CRCCssCode crcCssCode;
        try {
            crcCssCode = new CRCCssCode(cssCode);
            this.stylesheetsByURL.put(cssCode.getLink(), crcCssCode);

            if (this.stylesheetDependencies.containsKey(cssCode.getLink())) {
                LOG.log(Level.INFO, "{0} HTML documents have been waiting for this stylesheet!", (((List<CRCHtmlCode>) this.stylesheetDependencies.get(cssCode.getLink())).size()));
                for (CRCHtmlCode waitingDocument : ((List<CRCHtmlCode>) this.stylesheetDependencies.get(cssCode.getLink()))) {
                    if (this.areAllStylesheetsForDocumentAvailable(waitingDocument)) {
                        processSinglePage(waitingDocument);
                        documentsProcessed.add(waitingDocument);
                    }
                }
            }
            this.stylesheetDependencies.remove(cssCode.getLink());
            this.freeDocumentsFromPrison(documentsProcessed);
        } catch (CSSException ex) {
            ErrorMsg errMsg = new ErrorMsg();
            LOG.info("CSS document cannot be parsed!");
            errMsg.setMessage("CSS document cannot be parsed!");
            errMsg.setPage(cssCode.getLink());
            this.messageLogger.addMessage(errMsg);
        }
    }

    public void addHTMLPage(HTMLCode htmlCode) {
        LOG.log(Level.INFO, "Adding HTML page {0}", htmlCode.getLink().getUrl());

        CRCHtmlCode document = new CRCHtmlCode(htmlCode);

        if (this.areAllStylesheetsForDocumentAvailable(document)) {
            LOG.log(Level.INFO, "All styles are ready! Let's process this page");
            this.processSinglePage(document);
        } else {
            List<LinkURL> missingStylesheets = this.getMissingStylesheetsForDocument(document);
            LOG.log(Level.INFO, "There are {0} missing stylesheets!", missingStylesheets.size());
            for (LinkURL missingStylesheet : missingStylesheets) {
                if (!this.stylesheetDependencies.containsKey(missingStylesheet)) {
                    this.stylesheetDependencies.put(missingStylesheet, new ArrayList<CRCHtmlCode>());
                    LOG.log(Level.INFO, "Creating new dependency list for {0}", missingStylesheet.getUrl());
                }
                LOG.log(Level.INFO, "Adding this document to the dependency list of {0}", missingStylesheet.getUrl());
                ((List<CRCHtmlCode>) this.stylesheetDependencies.get(missingStylesheet)).add(document);
            }
        }
    }

    /**
     * This method frees resources allocated by HTML document caching.
     *
     * @param documents list of documents to be removed from the cache list
     */
    private void freeDocumentsFromPrison(List<CRCHtmlCode> documents) {
        for (List<CRCHtmlCode> stylesheetDepencyList : this.stylesheetDependencies.values()) {
            stylesheetDepencyList.removeAll(documents);
        }
    }

    /**
     * Return URLs of all stylesheet documents that are missing for the given
     * document. This method compares the list of stylesheet documents that have
     * been already supplied by the crawler and the list of stylesheets that are
     * required by the document itself (provided by CRCHtmlCode).
     *
     * @param document
     * @return
     */
    private List<LinkURL> getMissingStylesheetsForDocument(CRCHtmlCode document) {
        List<LinkURL> stylesheetsMissing = new ArrayList<>();
        for (LinkURL stylesheetRequired : document.getStylesheetFilesRequired()) {
            if (!this.stylesheetsByURL.containsKey(stylesheetRequired)) {
                stylesheetsMissing.add(stylesheetRequired);
            }
        }
        return stylesheetsMissing;
    }

    /**
     * Checks if all the stylesheet files required by the particular document
     * are available (have been provided by WebCrawler)
     *
     * @param document
     * @return
     */
    private boolean areAllStylesheetsForDocumentAvailable(CRCHtmlCode document) {
        return this.getMissingStylesheetsForDocument(document).isEmpty();
    }

    /**
     * This metod is called to process a single HTML page. It must be called
     * AFTER all required stylesheets are loaded. It prepares the document to be
     * analyzed by DOMAnalyzer. Then it calls doTheCheck method that actually
     * walks through the HTML DOM and evaluates HTML elements;
     *
     * @param document
     */
    private void processSinglePage(CRCHtmlCode document) {
	Document parsedDocument;
	
	try {
	    parsedDocument = DOMBuilder.jsoup2DOM(document.getHtmlCode().getParsedHTML());
	} catch(org.w3c.dom.DOMException ex) {
	    parsedDocument = null;
	}
	
	if(parsedDocument == null) {
	    if(this.messageLogger != null) this.messageLogger.addMessage(this.fillMessage(new ErrorMsg(), document.getHtmlCode().getLink(), "Cannot parse HTML document", null, 0));
	} else {
	
	    LOG.log(Level.INFO, "Processing HTML {0}", document.getHtmlCode().getLink());
	    LOG.log(Level.INFO, "{0} elements discovered", parsedDocument.getElementsByTagName("*").getLength());

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
	    LOG.log(Level.INFO, "{0} elements discovered AFTER CSS REMOVAL", parsedDocument.getElementsByTagName("*").getLength());

	    // add custom stylesheets
	    DOMAnalyzerEnhanced domAnalyzer = new DOMAnalyzerEnhanced(parsedDocument);
	    int logCustomElementsAdded = 0;

	    for(LinkURL stylesheetRequiredUrl : document.getStylesheetFilesRequired()) {
		if(this.stylesheetsByURL.containsKey(stylesheetRequiredUrl)){
		    try {
			LOG.log(Level.INFO, "Adding custom stylesheet - {0}", (new URL(stylesheetRequiredUrl.getUrl())).toString());
			domAnalyzer.addStyleSheet(new URL(stylesheetRequiredUrl.getUrl()), this.stylesheetsByURL.get(stylesheetRequiredUrl).getCssCode().getCodeCSS().getContent(), DOMAnalyzerEnhanced.Origin.AUTHOR);
			logCustomElementsAdded++;
		    } catch (MalformedURLException ex) {
			// that cannot happen (valid url set)
		    }
		}
	    }
	    domAnalyzer.getStyleSheets();
	    LOG.log(Level.INFO, "{0} stylesheets RE-ADDED into DOM", logCustomElementsAdded);

	    doTheCheck(parsedDocument, domAnalyzer, document);
	}
    }

    /**
     * Process the element's rules and add an usage for each of the rules.
     *
     * @param element Element to be processed
     * @param hasTextContent True if the element contains any text itself
     * (contains at least one Text Node)
     * @param domAnalyzer Dom analyzer for the given document
     * @param ruleUsage Usage to be added to the usage list for each rule
     */
    protected void applyDiscoveredRules(Element element, boolean hasTextContent, DOMAnalyzerEnhanced domAnalyzer, CSSRuleUsage ruleUsage){
	NodeData nodeData;
	nodeData = domAnalyzer.getElementStyleInherited((Element) element);		
	
	int logPropertiesNotRedundant = 0;
	
	if(nodeData != null) {		
	    Collection<String> cssPropertyNames;
	    cssPropertyNames = nodeData.getPropertyNames();

	    // load properties
            for (String cssPropertyName : cssPropertyNames) {

                // check the property is not inheritable (is applied without text contnet) or the element contains text
                if (!nodeData.getProperty(cssPropertyName).inherited() || hasTextContent) {

                    logPropertiesNotRedundant++;
                    //LOG.log(Level.INFO, "{0} is not redundant", nodeData.getValue(cssPropertyName, true).toString());

                    // load declaration sources
                    Declaration sourceDeclaration = nodeData.getSourceDeclaration(cssPropertyName, true);
                    if (sourceDeclaration != null) {
                        Declaration.Source source = sourceDeclaration.getSource();
                        if (source != null) {

                            // CRCCssCode resolve by url
                            LinkURL sourceUrl;
                            if (source.getUrl() == null) {   // inline style
                                LOG.info("This is AN inline style");
                                if (ruleUsage != null) {
                                    sourceUrl = ruleUsage.getUrl(); // not so nice
                                } else {
                                    sourceUrl = null;
                                }
                            } else {
                                sourceUrl = new LinkURL(source.getUrl().toString());
                            }

                            LOG.log(Level.INFO, "That was at {0} - {1}:{2}", new Object[]{sourceUrl.getUrl(), source.getLine(), source.getPosition()});
                            if (sourceUrl != null && this.stylesheetsByURL.containsKey(sourceUrl)) {
                                CRCCssCode cssDocument = this.stylesheetsByURL.get(sourceUrl);

                                // get particular CSS rule and add the usage
                                CSSRule cssRule = cssDocument.getCssRuleByPosition(new DeclarationPosition(source.getLine(), source.getPosition()));

                                if (cssRule != null) {
				    LOG.info("This property FOUND by position map");
                                } else {
                                    LOG.info("This property NOT found by position map");
                                }

                                if (cssRule != null && ruleUsage != null) {
                                    cssRule.addRuleUsage(ruleUsage);
                                }
                            }
                        }
                    }
                }

            }
        }

        LOG.log(Level.INFO, "{0} marked as not redundant", logPropertiesNotRedundant);

    }

    /**
     * This function iterates over the HTML DOM tree and call
     * applyDiscoveredRules on each Element Node
     *
     * @param parsedDocument Parsed document
     * @param domAnalyzer Dom analyzer for the given document
     * @param document Html document
     */
    protected void doTheCheck (Document parsedDocument, DOMAnalyzerEnhanced domAnalyzer, CRCHtmlCode document) {
	// walk through all elements (DFS)
	Node curNode;
	
	curNode = parsedDocument.getDocumentElement();
	
	while(curNode != null) {
	    if(curNode.hasChildNodes()) {
		Node nextNode = curNode.getFirstChild();

                // text child identified, we mark his parent as "has_text_content"
                if (nextNode.getNodeType() == Node.TEXT_NODE) {
                    if (curNode.getNodeType() == Node.ELEMENT_NODE && nextNode.getNodeValue().trim().length() > 0) {
                        ((Element) curNode).setAttribute("____CSSRC____has_test_content", "1");
                    }
                    curNode.removeChild(nextNode);  // we don't care about the text element any more
                } else if (nextNode.getNodeType() == Node.ELEMENT_NODE) {
                    // other than text element discovered, let's go deep
                    curNode = nextNode;
                } else {
                    // do nothing
                    curNode.removeChild(nextNode);
                }
            } else {
                // this node is already empty, youpee!
                Node nextNode = curNode.getParentNode();
                if (curNode.getParentNode() != null) {
                    curNode.getParentNode().removeChild(curNode);
                }
                if (curNode.getNodeType() == Node.ELEMENT_NODE) {
                    applyDiscoveredRules((Element) curNode, ((Element) curNode).hasAttribute("____CSSRC____has_test_content"), domAnalyzer, new CSSRuleUsage(document.getHtmlCode().getLink()));
                }
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
>>>>>>> branch 'dev-v0.3' of https://github.com/kozajaku/www-presentations-checker.git
		
<<<<<<< HEAD
		// text child identified, we mark his parent as "has_text_content"
		if(nextNode.getNodeType() == Node.TEXT_NODE) {
		    if(curNode.getNodeType() == Node.ELEMENT_NODE && nextNode.getNodeValue().trim().length() > 0) {
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
		if(curNode.getNodeType() == Node.ELEMENT_NODE) applyDiscoveredRules((Element) curNode, ((Element) curNode).hasAttribute("____CSSRC____has_test_content"), domAnalyzer, new CSSRuleUsage(document.getHtmlCode().getLink()) );
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
=======
         // load properties
         for(String cssPropertyName : cssPropertyNames) {
>>>>>>> branch 'dev-v0.3' of https://github.com/kozajaku/www-presentations-checker.git
			
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
     * This function is called when the checkup is finished. It sums up the
     * results, generates result messages and also process the rest of the
     * documents (those with missing stylesheets)
     *
     * @param traversalGraph unused
     */
    @Override
    public void finalizeCheckup(TraversalGraph traversalGraph) {
        // if some files are still waiting for some missing stylesheets, let's process them at the end of checkup
        Set<CRCHtmlCode> documentsToBeProcessed = new HashSet<>();
        for (List<CRCHtmlCode> documentList : this.stylesheetDependencies.values()) {
            for (CRCHtmlCode document : documentList) {
                documentsToBeProcessed.add(document);
            }
        }
        LOG.log(Level.INFO, "{0} documents have still missing stylesheet(s). Let''s process them now", documentsToBeProcessed.size());
        for (CRCHtmlCode documentToBeProcessed : documentsToBeProcessed) {
            processSinglePage(documentToBeProcessed);
        }

        // frees up memory
        List<CRCHtmlCode> documentsProcessed = new ArrayList<>();
        documentsProcessed.addAll(documentsToBeProcessed);
        this.freeDocumentsFromPrison(documentsProcessed);

        for (CRCCssCode cssDocument : this.stylesheetsByURL.values()) {
            List<CSSRuleSet> cssRuleBlocks = cssDocument.getCssRuleBlocks();
            for (CSSRuleSet cssRuleBlock : cssRuleBlocks) {

                MsgLocation blockMsgLocation = null;
                // because block can't be aimed by row:col, let's try to find the first rule at least..
                if (!cssRuleBlock.getCssRules().isEmpty()) {
                    CSSRule firstRule = cssRuleBlock.getCssRules().get(0);
                    if (firstRule != null) {
                        blockMsgLocation = new MsgLocation(firstRule.getDeclarationPosition().getLine(), firstRule.getDeclarationPosition().getCol());
                    }
                }

                if (cssRuleBlock.isRedundant()) {

                    this.messageLogger.addMessage(this.fillMessage(new WarningMsg(), cssDocument.getCssCode().getLink(),
                            "Whole rule block \"" + cssRuleBlock.toString() + "\" is redundant", blockMsgLocation, 10));
                } else {

                    // not whole CSS block is redundant, let's list only redundant css properties
                    for (CSSRule cssRule : cssRuleBlock.getCssRules()) {
                        if (cssRule.isRedundant()) {
                            this.messageLogger.addMessage(this.fillMessage(new WarningMsg(), cssDocument.getCssCode().getLink(),
                                    "Redundant rule \"" + cssRule.getName() + "\" in \"" + cssRuleBlock.toString() + "\"", new MsgLocation(cssRule.getDeclarationPosition().getLine(), cssRule.getDeclarationPosition().getCol()), 10));
                        }
                    }

                    // not whole CSS block is redundant, let's also generate the utilisation message
                    List<Integer> usageCounts = new ArrayList<>();
                    for (CSSRule cssRule : cssRuleBlock.getCssRules()) {
                        if (!cssRule.isRedundant()) {
                            usageCounts.add(cssRule.getCssRuleUsages().size());
                        }
                    }
                    this.messageLogger.addMessage(this.fillMessage(new InfoMsg(), cssDocument.getCssCode().getLink(), "Rule block \"" + cssRuleBlock.toString() + "\" has " + Collections.max(usageCounts) + " usages", blockMsgLocation, 0));
                }
            }
        }
    }

    /**
     * This is a helper method to create Message using inline style
     *
     * @param message Empty message to be filled with given data (ex. new ErrorMsg())
     * @param url URL of the document
     * @param text Text of the message
     * @param location Message location, can be null
     * @param priorityBoost priority boost of the message, default 0
     * @return The message filled with given arguments
     */
    protected Message fillMessage(Message message, LinkURL url, String text, MsgLocation location, int priorityBoost) {
	message.setPage(url);
	message.setMessage(text);
	message.setMsgLocation(location);
	message.setPriorityBoost(priorityBoost);
	return message;
    }  
}