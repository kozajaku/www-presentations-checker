/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper.helper;

import java.util.List;
import org.presentation.model.Domain;

/**
 *
 * @author petrof
 */
public class Functions {
    
    private Functions () { }
    
    public String getDomainsConcat(List<Domain> domains) {
	StringBuilder sb = new StringBuilder();
	
	if(domains.size() == 0) return "any";
	
	for(Domain d : domains) {
	    sb.append(d.getDomain()).append(", ");
	}
	
	return sb.toString();
    }
    
}
