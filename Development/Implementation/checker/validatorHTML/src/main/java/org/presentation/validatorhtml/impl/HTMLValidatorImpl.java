package org.presentation.validatorhtml.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.singlepagecontroller.SinglePageControllerService;
import org.w3.markup_validator.ErrorList;
import org.w3.markup_validator.MarkupValidationResponse;
import org.w3.markup_validator.ValidationErrors;
import org.w3.markup_validator.ValidationWarnings;
import org.w3.markup_validator.Warning;
import org.w3.markup_validator.WarningList;
import org.w3.soap_envelope.Envelope;

/**
 * HTML validator service.
 *
 * @author Jindřich Máca
 */
@Dependent
public class HTMLValidatorImpl implements SinglePageControllerService {

    /**
     * Package friendly constant for option interface.
     */
    static final String SERVICE_NAME = "HTML validator";
    private static final String validationService = "http://validator.w3.org/check";

    /**
     * Inject logger.
     */
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    /**
     * Message logger for this resource.
     */
    private MessageLogger logger;

    private InputStream getSOAPInputStream(String urlToValidate) throws MalformedURLException, IOException {
        URL url = new URL(validationService + "?output=soap12&uri=" + urlToValidate);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getInputStream();
    }

    private void processMVR(MarkupValidationResponse mvr){
        //TODO misto System.out.println naper co je potreba do tech loggeru - snad je rozhrani jasne

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
        for (org.w3.markup_validator.Error i: elist.getError()){
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
    
    @Override
    public void checkPage(ContentType contentType, LinkURL url, PageContent text) {
        LOG.log(Level.INFO, "Checking validity of html {0}", url.getUrl());
        try {
            InputStream stream = getSOAPInputStream(url.getUrl());
            JAXBContext context = JAXBContext.newInstance("org.w3.soap_envelope:org.w3.markup_validator", this.getClass().getClassLoader());
            Unmarshaller un = context.createUnmarshaller();
            Envelope envelope = (Envelope) ((JAXBElement<?>) un.unmarshal(stream)).getValue();
            for (Object i : envelope.getBody().getAny()) {
                i = ((JAXBElement<?>) i).getValue();
                if (i instanceof MarkupValidationResponse) {
                    MarkupValidationResponse mvr = (MarkupValidationResponse) i;
                    processMVR(mvr);
                    return;//done successfully
                }
            }
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "{0}", ex.getMessage());
        } catch (JAXBException ex) {
            LOG.log(Level.SEVERE, "unable to initialize JAXB parser", ex);
        }
    }

    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer) {
        logger = messageLoggerContainer.createLogger("HTML Validator");
    }

    @Override
    public String getID() {
        return SERVICE_NAME;
    }

    @Override
    public void stopChecking() {
        //not used method - should have empty body
    }

    @Override
    public boolean isApplicable(ContentType contentType) {
        return contentType.isHtml();
    }

}
