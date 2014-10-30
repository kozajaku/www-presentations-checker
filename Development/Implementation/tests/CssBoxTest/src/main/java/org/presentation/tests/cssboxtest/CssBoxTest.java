/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.tests.cssboxtest;


import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.CSSFactory;
import cz.vutbr.web.css.CSSProperty;
import cz.vutbr.web.css.CombinedSelector;
import cz.vutbr.web.css.Declaration;
import cz.vutbr.web.css.NodeData;
import cz.vutbr.web.css.RuleBlock;
import cz.vutbr.web.css.RuleSet;
import cz.vutbr.web.css.StyleSheet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.html.HTMLAnchorElement;
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
	
	DocumentSource docSource = new DefaultDocumentSource("http://www.webzdarma.cz/");

	//Parse the input document
	DOMSource parser = new DefaultDOMSource(docSource);
	
	
	//org.jsoup.nodes.Document jsoupDocument = Jsoup.parse(new URL());
	String docFolder = System.getProperty("user.dir") + File.separator + "testpage" + File.separator;
	String docFilename = docFolder + "index.html";
	String cssFilename = docFolder + "style.css";
	
	
	try {
	    StyleSheet s = CSSFactory.parse(cssFilename, "UTF-8");
	    
	    for(RuleBlock block : s) {
		if(block instanceof RuleSet) {
		    StringBuilder selectorString = new StringBuilder();
		    RuleSet rs = (RuleSet) block;
		    List<CombinedSelector> selectors = rs.getSelectors();
		    for(CombinedSelector selector : selectors) {
			selectorString.append(selector.toString()); // toString suppor?
			selectorString.append(", ");
		    }	
		    System.out.println(selectorString.substring(0, selectorString.length()-2));
		    for(Declaration d : rs) {
			System.out.println("\t\t" + d.getProperty());
			Declaration.Source source = d.getSource();
			System.out.println("\t\t\t\t" + source.getLine() + ":" + source.getPosition());
		    }
		}
	    }
	} catch (CSSException ex) {
	    Logger.getLogger(CssBoxTest.class.getName()).log(Level.SEVERE, null, ex);
	}
	

	org.jsoup.nodes.Document jsoupDocument = Jsoup.parse(new String(Files.readAllBytes(Paths.get(docFilename))));	
	
	Document doc = DOMBuilder.jsoup2DOM(jsoupDocument);
	
	//return;
	
	DOMAnalyzer da = new DOMAnalyzer(doc);
	
	//da.attributesToStyles(); //convert the HTML presentation attributes to inline styles
	da.addStyleSheet(null, new String(Files.readAllBytes(Paths.get(cssFilename))), DOMAnalyzer.Origin.AUTHOR);
	da.getStyleSheets(); //load the author style sheets
	
	NodeList nodeList = doc.getElementsByTagName("*");
	NodeData ndata;
		
	String outputFilename = System.getProperty("user.dir") + File.separator + "cssbox_test.txt";
	Logger.getLogger(CssBoxTest.class.getName()).log(Level.INFO, "WRITING LOG TO {0}", outputFilename);	
	PrintWriter writer = new PrintWriter(outputFilename, "UTF-8");
	
	
	walkThroughDoc(doc.getDocumentElement(), 0);
	return;
	
	long startTime = System.currentTimeMillis();		
		
	String spaces = "                                                                                                                                              ";
	
	for (int i = 0; i < nodeList.getLength(); i++) {
	    Node node = nodeList.item(i);
	    	    
	    if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.TEXT_NODE) {	
		ndata = da.getElementStyleInherited((Element) node);		    		
								
		writer.println(getNodeCaption(node));
			
		Collection<String> propertyNames = ndata.getPropertyNames();
		
		StringBuilder sb = new StringBuilder();
		
		for(String propertyName : propertyNames) {
		    sb.append("\t");
		    sb.append(propertyName);
		    sb.append(spaces.substring(0, Math.max(10, 30 - propertyName.length())));
                    CSSProperty property = ndata.getProperty(propertyName, true);
                    String valueStr = property.toString();
                    if (valueStr.equals("")){
                        if(ndata.getValue(propertyName, true) != null) {
			    valueStr = ndata.getValue(propertyName, true).toString();
			}
                    }

//		    Term<?> value = ndata.getValue(propertyName, true);
//                    property.
//		    String valueStr = property.toString();
//		    if(value == null) {
//                        System.out.println("null for propertyName: " + propertyName);
//			valueStr = "null";
//		    } else valueStr = value.toString();
		    
		    sb.append(valueStr);
		    sb.append(spaces.substring(0, Math.max(10, 30 - valueStr.length())));
		    //sb.append(value.toString());
		    //sb.append(spaces.substring(0, Math.max(1, 30 - value.length())));
		    
		    Declaration sourceDeclaration = ndata.getSourceDeclaration(propertyName, true);
		    Declaration.Source source = sourceDeclaration.getSource();
		    if(source == null) {
			sb.append("unknown source");
		    } else {
			sb.append(source.getUrl()).append(" - ").append(source.getLine()).append(":").append(source.getPosition());
		    }
		    sb.append(System.getProperty("line.separator"));
		}
		writer.println(sb.toString());
		
	    }
	}
	
	writer.println("---");
	writer.println("Run time: " + (System.currentTimeMillis() - startTime) + " ms");
	
	writer.close();

    }
    
    protected static String getNodeCaption(Node n) {
	boolean first = true;
	Node node = n;
	StringBuilder sb = new StringBuilder();
	while(node != null) {	  
	    if(node.getNodeType() == Node.TEXT_NODE) return node.getNodeValue();
	    
	    NamedNodeMap attrList = node.getAttributes();	    
	    Node classItem = null;
	    if(attrList != null) {
		classItem = attrList.getNamedItem("class");
	    }
	    sb.insert(0, node.getNodeName() + (classItem == null ? "" : "(" + classItem.getNodeValue() + ")") + (first ? "" : "/"));
	    node = node.getParentNode();
	    first = false;
	}
	return sb.toString();
    }
    
    protected static void walkThroughDoc(Node n, int depth) {
	String tab = "   ";	
	if(n.getNodeType() == Node.TEXT_NODE) {
	    if(getNodeCaption(n).trim().length() > 0){
		for(int i = 0; i < depth; i++) System.out.print(tab);
		System.out.print(getNodeCaption(n));
		System.out.println();
	    }
	}
	
	NodeList children = n.getChildNodes();
	for(int i = 0; i < children.getLength(); i++){
	    walkThroughDoc(children.item(i), depth+1);
	}
	
    }
    
    public static void dfs(Document parsedDocument){
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
		if(curNode.getNodeType() == Node.ELEMENT_NODE) System.out.println(getNodeCaption(curNode));
		curNode = nextNode;
	    }
	}	
    }
       
    
}

