package org.presentation.validatorcss.impl;

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
        return CSSValidatorImpl.SERVICE_NAME;
    }

    @Override
    public String getDestription() {
        return "Validation of CSS code";
    }

}
