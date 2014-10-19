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
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.singlepagecontroller.SinglePageControllerService;

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
        URL url = new URL(validationService + "?uri=" + urlToValidate + "&output=soap12");
        System.out.println(url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.connect();
        return connection.getInputStream();
    }

    @Override
    public void checkPage(ContentType contentType, LinkURL url, PageContent text) {
        LOG.log(Level.INFO, "Checking validity of html {0}", url.getUrl());
        try {
            InputStream input = getSOAPInputStream(url.getUrl());
            //implement soap representation
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "{0}", ex.getMessage());
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
