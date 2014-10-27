package org.presentation.validatorhtml.impl;

import javax.enterprise.context.Dependent;
import org.presentation.utils.AllowOptionService;

/**
 * <p>
 * OptionServiceImpl class.</p>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@Dependent
public class OptionServiceImpl implements AllowOptionService {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getID() {
        return HTMLValidatorImpl.SERVICE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDestription() {
        return "Validation of HTML code";//finally this method should not be used in final version
    }

}
