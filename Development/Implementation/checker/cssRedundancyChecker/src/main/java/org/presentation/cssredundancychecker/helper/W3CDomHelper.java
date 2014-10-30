/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.helper;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author petrof
 */
public class W3CDomHelper {
    
    public static boolean isNodeHTMLElement(Node node) {
	if(node.getNodeType() == Node.ELEMENT_NODE) {
	    return true;
	} else if (node.getNodeType() == Node.TEXT_NODE) {
	    if(node.getNodeValue().trim().length() > 0) {
		return true;
	    }
	}
	return false;
    }
    
    public static boolean isNodeHTMLEmpty(Node node) {
	NodeList childNodes = node.getChildNodes();
	for(int i = 0; i < childNodes.getLength(); i++) {
	    if(isNodeHTMLElement(childNodes.item(i))) {
		return false;
	    }	    
	}
	return true;	
    }
   
    
}
