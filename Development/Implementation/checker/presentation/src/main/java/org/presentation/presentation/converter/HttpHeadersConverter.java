package org.presentation.presentation.converter;


import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.presentation.model.Header;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author petrof
 */
@FacesConverter("HttpHeadersConverter")
public class HttpHeadersConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	
	List<Header> headers = new ArrayList<>();
	
	String[] lines = value.split("\\n");
	
	for(String line : lines) {
	    String[] splitted = line.split(": ", 2);
	    if(splitted.length == 2) {
		headers.add(new Header(splitted[0], splitted[1]));		
	    }
	}
	
	return headers;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
	
	StringBuilder sb = new StringBuilder();
	
	for(Header h : (List<Header>) value) {
	    sb.append(h.getKey()).append(": ").append(value).append("\\n");
	}
	
	return sb.toString();	
    }
    
}
