package org.presentation.kernel;

import java.util.ArrayList;
import java.util.List;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.utils.OptionContainer;

/**
 * This class serves as holder for passing information about new checking
 * request to {@link CheckRequestReceiver}.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class CheckingRequest {

    private LinkURL startingPoint;
    private List<Domain> allowedDomains = new ArrayList<>();
    private Integer pageLimit;
    private Integer maxDepth;
    private Integer requestInterval;
    private OptionContainer chosenOptions;
    private List<Header> headers = new ArrayList<>();

    /**
     * <p>
     * Getter for the field <code>startingPoint</code>.</p>
     *
     * @return a {@link org.presentation.model.LinkURL} object.
     */
    public LinkURL getStartingPoint() {
        return startingPoint;
    }

    /**
     * <p>
     * Setter for the field <code>startingPoint</code>.</p>
     *
     * @param startingPoint a {@link org.presentation.model.LinkURL} object.
     */
    public void setStartingPoint(LinkURL startingPoint) {
        this.startingPoint = startingPoint;
    }

    /**
     * <p>
     * Getter for the field <code>allowedDomains</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Domain> getAllowedDomains() {
        return allowedDomains;
    }

    /**
     * <p>
     * Setter for the field <code>allowedDomains</code>.</p>
     *
     * @param allowedDomains a {@link java.util.List} object.
     */
    public void setAllowedDomains(List<Domain> allowedDomains) {
        this.allowedDomains = allowedDomains;
    }

    /**
     * <p>
     * Getter for the field <code>pageLimit</code>.</p>
     *
     * @return a int.
     */
    public int getPageLimit() {
        return pageLimit;
    }

    /**
     * <p>
     * Setter for the field <code>pageLimit</code>.</p>
     *
     * @param pageLimit a int.
     */
    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    /**
     * <p>
     * Getter for the field <code>maxDepth</code>.</p>
     *
     * @return a int.
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * <p>
     * Setter for the field <code>maxDepth</code>.</p>
     *
     * @param maxDepth a int.
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * <p>
     * Getter for the field <code>requestInterval</code>.</p>
     *
     * @return a int.
     */
    public int getRequestInterval() {
        return requestInterval;
    }

    /**
     * <p>
     * Setter for the field <code>requestInterval</code>.</p>
     *
     * @param requestInterval a int.
     */
    public void setRequestInterval(int requestInterval) {
        this.requestInterval = requestInterval;
    }

    /**
     * <p>
     * Getter for the field <code>chosenOptions</code>.</p>
     *
     * @return a {@link org.presentation.utils.OptionContainer} object.
     */
    public OptionContainer getChosenOptions() {
        return chosenOptions;
    }

    /**
     * <p>
     * Setter for the field <code>chosenOptions</code>.</p>
     *
     * @param chosenOptions a {@link org.presentation.utils.OptionContainer}
     * object.
     */
    public void setChosenOptions(OptionContainer chosenOptions) {
        this.chosenOptions = chosenOptions;
    }

    /**
     * <p>
     * Getter for the field <code>headers</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Header> getHeaders() {
        return headers;
    }

    /**
     * <p>
     * Setter for the field <code>headers</code>.</p>
     *
     * @param headers a {@link java.util.List} object.
     */
    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

}
