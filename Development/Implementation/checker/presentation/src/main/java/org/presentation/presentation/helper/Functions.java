/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.presentation.model.Domain;
import org.presentation.persistence.model.ChosenOption;

/**
 *
 * @author petrof
 */
public final class Functions {
    
    private Functions () { }
    
    public static String getDomainsConcat(List<Domain> domains) {
	StringBuilder sb = new StringBuilder();
	
	if(domains.isEmpty()) return "any";
	
	for(Domain d : domains) {
	    sb.append(d.getDomain()).append(", ");
	}
	
	if(sb.length()>=2) sb.delete(sb.length()-2, sb.length()-1);
	return sb.toString();
    }
    
    public static String getEnumHumanReadable(ResourceBundle msg, Enum<?> e) {
	try {
	    String str = msg.getString(e.getClass().getSimpleName() + '.' + e.name());
	    return str;
	}
	catch(MissingResourceException ex) {
	    return e.name();
	}
    }
    
    public static String getDesiredCheckupsConcat(ResourceBundle msg, List<ChosenOption> opts) {
	StringBuilder sb = new StringBuilder();
	String t;
	for(ChosenOption o : opts) {
	    try {
		t = msg.getString("common.ch_" + o.getIdOption().toLowerCase());	    
		sb.append(o.getIdOption());
	    } catch(MissingResourceException ex) {
		sb.append(o.getIdOption());
	    }
	    sb.append(", ");
	}
	
	if(sb.length()>=2) sb.delete(sb.length()-2, sb.length()-1);
	return sb.toString();
    }
    
}
