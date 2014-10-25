/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker;

import java.util.logging.Logger;
import javax.inject.Inject;
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
    
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    MessageLogger messageLogger;
    boolean stopped;
    
    
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
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPage(ContentType contentType, LinkURL linkURL, PageContent pageContent, HTMLCode htmlCode) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void finalizeCheckup(TraversalGraph traversalGraph) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
