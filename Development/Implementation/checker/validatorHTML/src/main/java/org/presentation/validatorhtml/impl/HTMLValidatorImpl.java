package org.presentation.validatorhtml.impl;

import com.jcabi.w3c.Defect;
import com.jcabi.w3c.ValidationResponse;
import com.jcabi.w3c.ValidatorBuilder;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.ErrorMsg;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MsgLocation;
import org.presentation.model.logging.WarningMsg;
import org.presentation.singlepagecontroller.SinglePageControllerService;

/**
 * HTML validator service.
 *
 * @author Jindřich Máca
 */
@Dependent
public class HTMLValidatorImpl implements SinglePageControllerService {

    //package friendly constant
    static final String SERVICE_NAME = "HTML validator";
    
    /**
     * Inject logger.
     */
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    private MessageLogger logger;

    @Override
    public void checkPage(ContentType contentType, LinkURL url, PageContent text) {
        LOG.log(Level.INFO, "Checking validity of html {0}", url.getUrl());
        try {
            ValidationResponse response = new ValidatorBuilder().html().validate(text.getContent());
            Set<Defect> errors = response.errors();
            for (Defect error : errors) {
                ErrorMsg msg = new ErrorMsg();
                msg.setPage(url);
                msg.setMessage(error.message());
                msg.setMsgLocation(new MsgLocation(error.line(), error.column()));
                logger.addMessage(msg);
            }
            Set<Defect> warnings = response.warnings();
            for (Defect warning : warnings) {
                WarningMsg msg = new WarningMsg();
                msg.setPage(url);
                msg.setMessage(warning.message());
                msg.setMsgLocation(new MsgLocation(warning.line(), warning.column()));
                logger.addMessage(msg);
            }
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "HTML validation failed on {0}", url.getUrl());
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
