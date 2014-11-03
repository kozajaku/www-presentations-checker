package org.presentation.webcrawler.impl;

import javax.enterprise.context.Dependent;
import org.presentation.utils.AllowOptionService;

/**
 * <p>
 * OptionServiceImpl class.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class OptionServiceImpl implements AllowOptionService {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getID() {
        return CrawlerServiceDefault.CHECK_LINKS_OPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDestription() {
        return "Check links";//finally this method should not be used in final version
    }

}
