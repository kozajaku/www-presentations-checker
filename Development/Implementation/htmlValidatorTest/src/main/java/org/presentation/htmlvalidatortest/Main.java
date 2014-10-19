
package org.presentation.htmlvalidatortest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.w3._2003._05.soap_envelope.Envelope;
import org.w3._2005._10.markup_validator.ErrorList;
import org.w3._2005._10.markup_validator.MarkupValidationResponse;
import org.w3._2005._10.markup_validator.ValidationErrors;
import org.w3._2005._10.markup_validator.ValidationWarnings;
import org.w3._2005._10.markup_validator.Warning;
import org.w3._2005._10.markup_validator.WarningList;

/**
 *
 * @author radio.koza
 */
public class Main {
    private static final String HTML_TEST_PAGE = "http://www.webzdarma.cz/";
    
    private static void getInfo(MarkupValidationResponse mvr){
        //uri
        System.out.println("URI: " + mvr.getUri());
        //charset
        System.out.println("Charset: " + mvr.getCharset());
        //checked by
        System.out.println("Checked by: " + mvr.getCheckedby());
        //doctype
        System.out.println("Doctype: " + mvr.getDoctype());
        //validity
        System.out.println("Validity: " + Boolean.toString(mvr.isValidity()));
        //errors
        ValidationErrors errors = mvr.getErrors();
        System.out.println("Errors count: " + errors.getErrorcount());
        ErrorList elist = errors.getErrorlist();
        for (org.w3._2005._10.markup_validator.Error i: elist.getError()){
            System.out.println("Error: " + i.getMessage());
        }
        //warnings
        ValidationWarnings warnings = mvr.getWarnings();
        System.out.println("Warnings count: " + warnings.getWarningcount());
        WarningList wlist = warnings.getWarninglist();
        for (Warning w: wlist.getWarning()){
            System.out.println("Warning: " + w.getMessage());
        }
        
    }
    
    public static void main(String[] args) throws JAXBException, IOException {
        Receiver receiver = new Receiver();
        InputStream stream = receiver.getSoapResponse(HTML_TEST_PAGE);
        
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        String line;
//        while ((line = reader.readLine()) != null){
//            System.out.println(line);
//        }
//        if (true){
//            return;
//        }
        JAXBContext context = JAXBContext.newInstance("org.w3._2003._05.soap_envelope:org.w3._2005._10.markup_validator");
        Unmarshaller un = context.createUnmarshaller();
        Envelope envelope = (Envelope) ((JAXBElement<?>)un.unmarshal(stream)).getValue();
        for (Object i : envelope.getBody().getAny()){
            i = ((JAXBElement<?>)i).getValue();
            if (i instanceof MarkupValidationResponse){
                MarkupValidationResponse mvr = (MarkupValidationResponse) i;
                getInfo(mvr);
                return;
            }
        }
    }
}
