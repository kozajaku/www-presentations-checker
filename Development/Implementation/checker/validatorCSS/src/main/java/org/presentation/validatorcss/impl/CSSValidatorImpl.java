package org.presentation.validatorcss.impl;

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
import org.w3._2003._05.soap_envelope.Envelope;
import org.w3._2005._07.css_validator.CSSValidationResponse;
import org.w3._2005._07.css_validator.ErrorList;
import org.w3._2005._07.css_validator.Result;
import org.w3._2005._07.css_validator.ValidationErrors;
import org.w3._2005._07.css_validator.ValidationWarnings;
import org.w3._2005._07.css_validator.Warning;
import org.w3._2005._07.css_validator.WarningList;

/**
 * CSS validator service.
 *
 * @author Jindřich Máca
 */
@Dependent
public class CSSValidatorImpl implements SinglePageControllerService {

    /**
     * Package friendly constant for option interface.
     */
    static final String SERVICE_NAME = "CSS validator";
    private static final String VALIDATION_SERVICE = "http://jigsaw.w3.org/css-validator/validator";

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
        URL url = new URL(VALIDATION_SERVICE + "?output=soap12&uri=" + urlToValidate);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getInputStream();
    }
    
    private void processCVR(CSSValidationResponse cvr){
        //TODO zase to samy tuniku

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

    @Override
    public void checkPage(ContentType contentType, LinkURL url, PageContent text) {
        LOG.log(Level.INFO, "Checking validity of css {0}", url.getUrl());
        try {
            InputStream stream = getSOAPInputStream(url.getUrl());
            JAXBContext context = JAXBContext.newInstance("org.w3._2003._05.soap_envelope:org.w3._2005._07.css_validator", this.getClass().getClassLoader());
            Unmarshaller un = context.createUnmarshaller();
            Envelope envelope = (Envelope) ((JAXBElement<?>) un.unmarshal(stream)).getValue();
            for (Object i : envelope.getBody().getAny()) {
                i = ((JAXBElement<?>) i).getValue();
                if (i instanceof CSSValidationResponse) {
                    CSSValidationResponse cvr = (CSSValidationResponse) i;
                    processCVR(cvr);
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
        logger = messageLoggerContainer.createLogger("CSS Validator");
    }

    @Override
    public String getID() {
        return SERVICE_NAME;
    }

    @Override
    public void stopChecking() {
        //nothing to do - body should be empty
    }

    @Override
    public boolean isApplicable(ContentType contentType) {
        return contentType.isCss();
    }

}
