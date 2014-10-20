/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

import java.text.MessageFormat;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.presentation.model.Domain;
import org.presentation.model.logging.Message;
import org.presentation.persistence.model.ChosenOption;

/**
 * Custom function bundle used by view templates
 *
 * @author petrof
 * @version $Id: $Id
 */
public final class Functions {
    
    private Functions () { }
    
    /**
     * This function returns comma-separated list from List of Domain objects
     *
     * @param domains domain list
     * @return comma-separated list
     */
    public static String getDomainsConcat(List<Domain> domains) {
	StringBuilder sb = new StringBuilder();
	
	if(domains.isEmpty()) return "any";
	
	for(Domain d : domains) {
	    sb.append(d.getDomain()).append(", ");
	}
	
	if(sb.length()>=2) sb.delete(sb.length()-2, sb.length()-1);
	return sb.toString();
    }
    
    /**
     * This function supports dynamic enum i18n
     *
     * @param msg resource bundle for i18n
     * @param e enum value to be translated
     * @return translated enum value
     */
    public static String getEnumHumanReadable(ResourceBundle msg, Enum<?> e) {
	try {
	    String str = msg.getString(e.getClass().getSimpleName() + '.' + e.name());
	    return str;
	}
	catch(MissingResourceException ex) {
	    return e.name();
	}
    }
    
    /**
     * This function returns comma-separated list from List of ChosenOption objects
     *
     * @param msg resource bundle for i18n
     * @param opts option list
     * @return comma-separated list
     */
    public static String getDesiredCheckupsConcat(ResourceBundle msg, List<ChosenOption> opts) {
	StringBuilder sb = new StringBuilder();
	String t;
	for(ChosenOption o : opts) {
	    try {
		t = msg.getString("common.ch_" + o.getIdOption().toLowerCase());	    
		sb.append(t);
	    } catch(MissingResourceException ex) {
		sb.append(o.getIdOption());
	    }
	    sb.append(", ");
	}
	
	if(sb.length()>=2) sb.delete(sb.length()-2, sb.length()-1);
	return sb.toString();
    }
    
    /**
     * This function allows to user parametrized messages (such as "there is {0} posts")
     *
     * @param msg parametrized message
     * @param p1 parameter to be replaced
     * @return formatted message
     */
    public static String translate(String msg, String p1) {	
	return MessageFormat.format(msg, p1);
    }
    
    /**
     * This function translates the message type
     *
     * @param msg resource bundle for i18n
     * @param messageObject message object
     * @return a {@link java.lang.String} object.
     */
    public static String getMessageTypeCaption(ResourceBundle msg, Message messageObject) {
	String className = messageObject.getClass().getSimpleName();
	if(msg == null) return className;
	try {
	    return msg.getString("common.msg_type_" + className.toLowerCase());
	}
	catch(MissingResourceException ex) {
	    return className;
	}
    }
    
    /**
     * this function replaces all non-alphanumeric characters by '-'
     *
     * @param s input string
     * @return sanitized string
     */
    public static String normalizeString (String s) {
	return s.replaceAll("[^a-zA-Z]+", "-");
    }
    
}
