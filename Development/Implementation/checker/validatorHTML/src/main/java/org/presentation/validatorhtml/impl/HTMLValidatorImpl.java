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
import org.presentation.model.logging.ErrorMsg;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MsgLocation;
import org.presentation.model.logging.WarningMsg;
import org.presentation.singlepagecontroller.SinglePageControllerService;
import org.w3.markup_validator.ErrorList;
import org.w3.markup_validator.MarkupValidationResponse;
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
    /**
     * Constant representing W3C url of validation service for HTML.
     */
    private static final String VALIDATION_SERVICE = "http://validator.w3.org/check";

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

    /**
     * Returns input stream with SOAP respons of HTML W3C validation service.
     *
     * @param urlToValidate URL which will be validated
     * @return Input stream wich SOAP respons of HTML W3C validation service.
     * @throws MalformedURLException If an unknown protocol is specified
     * @throws IOException If request fails in any way
     */
    private InputStream getSOAPInputStream(String urlToValidate) throws MalformedURLException, IOException {
        URL url = new URL(VALIDATION_SERVICE + "?output=soap12&uri=" + urlToValidate);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getInputStream();
    }

    /**
     * Process prepared SOAP response for specific URL to relevant messages for
     * user.
     *
     * @param mvr Prepared SOAP response
     * @param url URL releated to validation
     */
    private void processMVR(MarkupValidationResponse mvr, LinkURL url) {
        if (!mvr.isValidity()) {
            WarningList warnings = mvr.getWarnings().getWarninglist();
            for (Warning warning : warnings.getWarning()) {
                WarningMsg msg = new WarningMsg();
                msg.setPage(url);
                msg.setMessage(warning.getMessage());
                msg.setMsgLocation(new MsgLocation(new Integer(warning.getLine()), new Integer(warning.getCol())));
                logger.addMessage(msg);
            }
            LOG.log(Level.INFO, "Logged {0} HTML warnings on page: {1}", new Object[]{mvr.getWarnings().getWarningcount(), url.getUrl()});
            ErrorList errors = mvr.getErrors().getErrorlist();
            for (org.w3.markup_validator.Error error : errors.getError()) {
                ErrorMsg msg = new ErrorMsg();
                msg.setPage(url);
                msg.setMessage(error.getMessage());
                msg.setMsgLocation(new MsgLocation(new Integer(error.getLine()), new Integer(error.getCol())));
                logger.addMessage(msg);
            }
            LOG.log(Level.INFO, "Logged {0} HTML errors on page: {1}", new Object[]{mvr.getErrors().getErrorcount(), url.getUrl()});
        } else {
            LOG.log(Level.INFO, "Validation of HTML page {0} contains no warning or errors.", url.getUrl());
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
                    processMVR(mvr, url);
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
