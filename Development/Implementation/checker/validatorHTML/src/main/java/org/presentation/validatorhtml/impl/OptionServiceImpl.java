package org.presentation.validatorhtml.impl;

import javax.enterprise.context.Dependent;
import org.presentation.utils.AllowOptionService;

/**
 *
 * @author radio.koza
 */
@Dependent
public class OptionServiceImpl implements AllowOptionService {

    @Override
    public String getID() {
        return HTMLValidatorImpl.SERVICE_NAME;
    }

    @Override
    public String getDestription() {
        return "Validation of HTML code";//finally this method should not be used in final version
    }

}
