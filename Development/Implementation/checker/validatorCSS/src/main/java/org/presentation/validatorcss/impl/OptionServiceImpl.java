package org.presentation.validatorcss.impl;

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
        return CSSValidatorImpl.SERVICE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDestription() {
        return Property.getInstance().getStringPropery("CSS_VALIDATOR_DESCRIPTION");
    }

}
