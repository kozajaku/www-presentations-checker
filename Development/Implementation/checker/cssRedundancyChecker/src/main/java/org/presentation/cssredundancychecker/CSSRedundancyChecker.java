package org.presentation.cssredundancychecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.presentation.cssredundancychecker.model.CRCCssCode;
import org.presentation.cssredundancychecker.model.CRCHtmlCode;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.parser.CSSCode;
import org.presentation.parser.HTMLCode;
import org.presentation.wholepresentationcontroller.WholePresentationChecker;

/**
 *
 * @author Adam
 */
public class CSSRedundancyChecker implements WholePresentationChecker {

    Map<LinkURL, List<CRCHtmlCode>> stylesheetDependencies;
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
        this.cssCodes.add(new CRCCssCode(cssCode));

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
        // todo
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

        // todo
    }

}
