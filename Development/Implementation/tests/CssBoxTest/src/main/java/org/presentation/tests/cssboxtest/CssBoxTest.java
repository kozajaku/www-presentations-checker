/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.tests.cssboxtest;


import cz.vutbr.web.css.NodeData;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author petrof
 */
public class CssBoxTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SAXException, IOException {
	
	//Open the network connection 
	DocumentSource docSource = new DefaultDocumentSource("http://seznam.cz/");

	//Parse the input document
	DOMSource parser = new DefaultDOMSource(docSource);
	org.jsoup.nodes.Document jsoupDocument = Jsoup.parse(docSource.getURL(), 0);
	
	Document doc = DOMBuilder.jsoup2DOM(jsoupDocument);
	
	DOMAnalyzer da = new DOMAnalyzer(doc, docSource.getURL());
	
	da.attributesToStyles(); //convert the HTML presentation attributes to inline styles
	da.getStyleSheets(); //load the author style sheets
	
	NodeList nodeList = doc.getElementsByTagName("*");
	NodeData ndata;
		
	for (int i = 0; i < nodeList.getLength(); i++) {
	    Node node = nodeList.item(i);
	    //Logger.getLogger(TestCssBox.class.getName()).log(Level.INFO, "- {0}", node.getNodeName());	
	    
	    if (node.getNodeType() == Node.ELEMENT_NODE) {
		ndata = da.getElementStyleInherited((Element) node);		
			
		Logger.getLogger(CssBoxTest.class.getName()).log(Level.INFO, "- {0}", ((Element)node).getNodeName());	
		
		Logger.getLogger(CssBoxTest.class.getName()).log(Level.INFO, ndata.getPropertyNames().stream().collect(Collectors.joining(", ")));		
		
		//for()
	    }
	}
	

    }
       
    
}

