/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.helper;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * W3CDomHelper class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class W3CDomHelper {

    /**
     * <p>
     * isNodeHTMLElement.</p>
     *
     * @param node a {@link org.w3c.dom.Node} object.
     * @return a boolean.
     */
    public static boolean isNodeHTMLElement(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            return true;
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            if (node.getNodeValue().trim().length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * isNodeHTMLEmpty.</p>
     *
     * @param node a {@link org.w3c.dom.Node} object.
     * @return a boolean.
     */
    public static boolean isNodeHTMLEmpty(Node node) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (isNodeHTMLElement(childNodes.item(i))) {
                return false;
            }
        }
        return true;
    }

}
