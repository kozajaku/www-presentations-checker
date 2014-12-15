package org.presentation.validatorhtml.impl;

import javax.enterprise.context.Dependent;
import org.presentation.utils.AllowOptionService;
import org.presentation.utils.Property;

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
        return Property.getInstance().getStringPropery("HTML_VALIDATOR_DESCRIPTION");//finally this method should not be used in final version
    }

}
