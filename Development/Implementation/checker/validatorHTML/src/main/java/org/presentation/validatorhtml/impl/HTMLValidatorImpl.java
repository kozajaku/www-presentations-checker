package org.presentation.validatorhtml.impl;

import java.io.IOException;
import java.io.InputStream;
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
import org.presentation.model.logging.InfoMsg;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MsgLocation;
import org.presentation.model.logging.WarningMsg;
import org.presentation.singlepagecontroller.SinglePageChecker;
import org.presentation.validatorjaxbutils.ValidatorResponseFetcher;
import org.w3._2003._05.soap_envelope.Envelope;
import org.w3._2005._10.markup_validator.ErrorList;
import org.w3._2005._10.markup_validator.MarkupValidationResponse;
import org.w3._2005._10.markup_validator.Warning;
import org.w3._2005._10.markup_validator.WarningList;

/**
 * {@link org.presentation.singlepagecontroller.SinglePageControllerService}
 * representing HTML validator service. Sends
 * {@link org.presentation.model.LinkURL} of HTML file to W3C validation service
 * and processes its SOAP response to
 * {@link org.presentation.model.logging.MessageLogger}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
@Dependent
public class HTMLValidatorImpl implements SinglePageChecker {

    /**
     * Package friendly constant for option interface.
     */
    static final String SERVICE_NAME = "HTML validator";
    //Constant representing W3C url of validation service for HTML
    private static final String VALIDATION_SERVICE = "http://validator.w3.org/check";

    //Inject logger
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    //Message logger for this control
    private MessageLogger logger;

    /**
     * Returns {@link InputStream} with SOAP respons of HTML W3C validation
     * service.
     *
     * @param urlToValidate {@link String} URL which will be validated
     * @return {@link InputStream} with SOAP respons of HTML W3C validation
     * service
     * @throws IOException If request fails in any way.
     */
    private InputStream getSOAPInputStream(String urlToValidate) throws IOException {
        return ValidatorResponseFetcher.fetchSOAPResponse(VALIDATION_SERVICE, urlToValidate);
    }

    /**
     * Process prepared SOAP response of W3C validation service for this
     * {@link LinkURL} of HTML file to {@link MessageLogger} for this control.
     *
     * @param mvr {@link CSSValidationResponse} of W3C validation service for
     * this {@link LinkURL} of HTML file
     * @param url {@link LinkURL} of validated HTML file
     */
    private void processMVR(MarkupValidationResponse mvr, LinkURL url) {
        if (!mvr.isValidity()) {
            WarningList warnings = mvr.getWarnings().getWarninglist();
            for (Warning warning : warnings.getWarning()) {
                WarningMsg msg = new WarningMsg();
                msg.setPage(url);
                msg.setMessage(warning.getMessage().trim());
                if (warning.getLine() != null && warning.getCol() != null) {
                    msg.setMsgLocation(new MsgLocation(Integer.valueOf(warning.getLine()), Integer.valueOf(warning.getCol())));
                }
                logger.addMessage(msg);
            }
            LOG.log(Level.INFO, "Logged {0} HTML warnings on page: {1}", new Object[]{mvr.getWarnings().getWarningcount(), url.getUrl()});
            ErrorList errors = mvr.getErrors().getErrorlist();
            for (org.w3._2005._10.markup_validator.Error error : errors.getError()) {
                ErrorMsg msg = new ErrorMsg();
                msg.setPage(url);
                msg.setMessage(error.getMessage().trim());
                if (error.getLine() != null && error.getCol() != null) {
                    msg.setMsgLocation(new MsgLocation(Integer.valueOf(error.getLine()), Integer.valueOf(error.getCol())));
                }
                logger.addMessage(msg);
            }
            LOG.log(Level.INFO, "Logged {0} HTML errors on page: {1}", new Object[]{mvr.getErrors().getErrorcount(), url.getUrl()});
            WarningMsg msg = new WarningMsg();
            msg.setPage(url);
            msg.setMessage("HTML page contains " + mvr.getWarnings().getWarningcount() + " warnings and " + mvr.getErrors().getErrorcount() + " errors.");
            logger.addMessage(msg);
        } else {
            InfoMsg msg = new InfoMsg();
            msg.setPage(url);
            msg.setMessage("Validation of HTML page contains no warning or errors.");
            logger.addMessage(msg);
            LOG.log(Level.INFO, "Validation of HTML page {0} contains no warning or errors.", url.getUrl());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkPage(ContentType contentType, LinkURL url, PageContent text) {
        LOG.log(Level.INFO, "Checking validity of html {0}", url.getUrl());
        try {
            InputStream stream = getSOAPInputStream(url.getUrl());
            JAXBContext context = JAXBContext.newInstance("org.w3._2003._05.soap_envelope:org.w3._2005._10.markup_validator", this.getClass().getClassLoader());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer) {
        logger = messageLoggerContainer.createLogger("HTML Validator");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getID() {
        return SERVICE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopChecking() {
        //not used method - should have empty body
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isApplicable(ContentType contentType) {
        return contentType.isHtml();
    }

}
