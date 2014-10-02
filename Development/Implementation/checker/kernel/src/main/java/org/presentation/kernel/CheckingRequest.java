package org.presentation.kernel;

import java.util.List;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.persistence.model.Domain;
import org.presentation.utils.OptionContainer;

/**
 *
 * @author radio.koza
 */
public class CheckingRequest {

    private LinkURL startingPoint;
    private List<Domain> allowedDomains;
    private int pageLimit;
    private int maxDepth;
    private int requestInterval;
    private OptionContainer chosenOptions;
    private List<Header> headers;

    public LinkURL getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(LinkURL startingPoint) {
        this.startingPoint = startingPoint;
    }

    public List<Domain> getAllowedDomains() {
        return allowedDomains;
    }

    public void setAllowedDomains(List<Domain> allowedDomains) {
        this.allowedDomains = allowedDomains;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getRequestInterval() {
        return requestInterval;
    }

    public void setRequestInterval(int requestInterval) {
        this.requestInterval = requestInterval;
    }

    public OptionContainer getChosenOptions() {
        return chosenOptions;
    }

    public void setChosenOptions(OptionContainer chosenOptions) {
        this.chosenOptions = chosenOptions;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }
    
    
}
