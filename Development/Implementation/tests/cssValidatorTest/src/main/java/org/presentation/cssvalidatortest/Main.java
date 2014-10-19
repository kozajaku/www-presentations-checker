package org.presentation.cssvalidatortest;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.w3._2003._05.soap_envelope.Envelope;
import org.w3._2005._07.css_validator.CSSValidationResponse;
import org.w3._2005._07.css_validator.ErrorList;
import org.w3._2005._07.css_validator.Result;
import org.w3._2005._07.css_validator.ValidationErrors;
import org.w3._2005._07.css_validator.ValidationWarnings;
import org.w3._2005._07.css_validator.Warning;
import org.w3._2005._07.css_validator.WarningList;

/**
 *
 * @author radio.koza
 */
public class Main {
    private static final String CSS_TEST_PAGE = "https://www.seznam.cz/";
    
    
    private static void getInfo(CSSValidationResponse cvr){
        //uri
        System.out.println("URI: " + cvr.getUri());
        //checked by
        System.out.println("Checked by: " + cvr.getCheckedby());
        //css level
        System.out.println("CSS level: " + cvr.getCsslevel());
        //validity
        System.out.println("Validity: " + Boolean.toString(cvr.isValidity()));
        Result res = cvr.getResult();
        //errors
        ValidationErrors errors = res.getErrors();
        System.out.println("Errors count: " + errors.getErrorcount());
        for (ErrorList elist: errors.getErrorlist()){
            System.out.println("Errorlist uri: " + elist.getUri());
            for (org.w3._2005._07.css_validator.Error e: elist.getError()){
                System.out.println("Error: " + e.getMessage().trim());
            }
        }
        //warnings
        ValidationWarnings warns = res.getWarnings();
        System.out.println("Warnings count: " + warns.getWarningcount());
        for (WarningList wlist: warns.getWarninglist()){
            System.out.println("Warnlist uri: " + wlist.getUri());
            for (Warning w: wlist.getWarning()){
                System.out.println("Warning: " + w.getMessage().trim());
            }
        }
    }
    
    public static void main(String[] args) throws JAXBException, IOException {
        Receiver receiver = new Receiver();
        InputStream stream = receiver.getSoapResponse(CSS_TEST_PAGE);
        
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        String line;
//        while ((line = reader.readLine()) != null){
//            System.out.println(line);
//        }
//        if (true){
//            return;
//        }
        JAXBContext context = JAXBContext.newInstance("org.w3._2003._05.soap_envelope:org.w3._2005._07.css_validator");
        Unmarshaller un = context.createUnmarshaller();
        Envelope envelope = (Envelope) ((JAXBElement<?>)un.unmarshal(stream)).getValue();
        for (Object i : envelope.getBody().getAny()){
            i = ((JAXBElement<?>)i).getValue();
            if (i instanceof CSSValidationResponse){
                CSSValidationResponse cvr = (CSSValidationResponse) i;
                getInfo(cvr);
                return;
            }
        }
    }
}
