/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.tests.cssrctest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.presentation.cssredundancychecker.CSSRedundancyChecker;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.Message;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.parser.CSSCode;
import org.presentation.parser.HTMLCode;

/**
 *
 * @author petrof
 */
public class CSSRCTest {

    public static void main(String[] args) throws IOException {
	CSSRedundancyChecker cssrc = new CSSRedundancyChecker();
	
	MessageLoggerContainer mlc = new MessageLoggerContainer();
	MessageLogger messageLogger = mlc.createLogger("cssrc");	
	
	cssrc.offerMsgLoggerContainer(mlc);
	
	String docFolder = System.getProperty("user.dir") + File.separator + ".." + File.separator + ".." + File.separator + "Test Website" + File.separator + "public_html" + File.separator;
	String docFilename = docFolder + "index.html";
	String cssFilename = docFolder + "css" + File.separator + "style.css";
	PageContent pc;
	LinkURL lu;

	pc = new PageContent(new String(Files.readAllBytes(Paths.get(docFilename))));
	lu = new LinkURL("http://mojestranka.cz");
	cssrc.addPage(new ContentType("text/html"), lu, pc, new HTMLCode(pc, lu));

	pc = new PageContent(new String(Files.readAllBytes(Paths.get(cssFilename))));
	lu = new LinkURL("http://mojestranka.cz/css/style.css");
	cssrc.addPage(new ContentType("text/html"), lu, pc, new CSSCode(pc, lu));
	
	cssrc.finalizeCheckup(null);
	
	for(List<Message> messages : mlc.generateMsgReport().getMsgGroups().values()) {
	   for(Message message : messages) {
	       System.out.println(message.getMessage() + " at " + message.getPage().getUrl());
	   }
	}
	
    }       
    
}
