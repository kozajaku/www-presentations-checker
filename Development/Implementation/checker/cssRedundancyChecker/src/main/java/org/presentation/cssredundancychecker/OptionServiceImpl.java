package org.presentation.cssredundancychecker;

import javax.enterprise.context.Dependent;
import org.presentation.utils.AllowOptionService;

/**
 * <p>
 * OptionServiceImpl class.</p>
 *
 * @author radio.koza
 */
@Dependent
public class OptionServiceImpl implements AllowOptionService {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getID() {
        return CSSRedundancyChecker.SERVICE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDestription() {
        return "Checker for CSS redundancy";//finally this method should not be used in final version
    }

}
