/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.tests.htmlparsedstructurememorytest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author petrof
 */
public class Main {
    
    public static void main(String[] args) throws SAXException, IOException, InterruptedException {
	
	List<Document> documents = new ArrayList<>();
	Document doc;	
	org.jsoup.nodes.Document jsoupDocument;
	
	
	for(int i = 0; i<1000; i++) {
	    try {
		jsoupDocument = Jsoup.parse(new URL("http://idnes.cz/"), 10000);	
		doc = DOMBuilder.jsoup2DOM(jsoupDocument);
		documents.add(doc);
		System.out.println(documents.size());
	    }
	    catch(Exception e) {
		i--;
		Thread.sleep(5000);
	    }
	}
	
	
    }
    
}


