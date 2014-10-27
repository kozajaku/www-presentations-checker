/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.presentation.kernel.CheckRequestReceiver;
import org.presentation.kernel.CheckingRequest;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.ChosenOption;
import org.presentation.presentation.exception.UserAuthorizationException;
import org.presentation.presentation.validation.ValidUrl;
import org.presentation.utils.AllowOptionService;
import org.presentation.utils.OptionContainer;

/**
 * This bean is used to create new checkups
 *
 * @author petrof
 * @version $Id: $Id
 */
@Named
@RequestScoped
public class NewCheckupBean extends ProtectedBean {

    // todo - may be unused
    /**
     * Constant <code>CHK_CSS_REDUNDANCY="CSS_REDUNDANCY"</code>
     */
    protected static final String CHK_CSS_REDUNDANCY = "CSS_REDUNDANCY";
    /**
     * Constant <code>CHK_CSS_VALIDATION="CSS_VALIDATION"</code>
     */
    protected static final String CHK_CSS_VALIDATION = "CSS_VALIDATION";
    /**
     * Constant <code>CHK_HTML_VALIDATION="HTML_VALIDATION"</code>
     */
    protected static final String CHK_HTML_VALIDATION = "HTML_VALIDATION";
    /**
     * Constant <code>CHK_DEAD_LINKS="DEAD_LINKS"</code>
     */
    protected static final String CHK_DEAD_LINKS = "DEAD_LINKS";

    @NotNull
    @ValidUrl
    protected String startingLink;

    protected String[] desiredCheckups;

    protected List<Domain> domainsAllowed = new ArrayList<>();

    @NotNull
    @Min(1)
    @Max(20)
    protected int maxCrawlingDepth = 5;

    @NotNull
    @Min(500)
    @Max(10000)
    protected int minRequestInterval = 2000;

    @NotNull
    @Min(1)
    @Max(100000)
    protected int pageLimit = 50;

    protected List<Header> httpHeaders = new ArrayList<>();

    @Min(0)
    protected int previousCheckupId = 0;

    @EJB
    protected CheckRequestReceiver checkRequestReceiver;

    @Inject
    @Any
    protected Instance<AllowOptionService> optionServicePrototype;

    /**
     * this method gets all checkups available
     *
     * @return all checkups available
     */
    public Map<String, Object> getCheckupsAvailable() {
        Map<String, Object> checkupsAvailable = new HashMap<>();

        for (AllowOptionService i : optionServicePrototype) {
            try {
                checkupsAvailable.put(msg.getString("common.ch_" + i.getID().trim().replaceAll(" ", "_").toLowerCase()), i.getID());
            } catch (MissingResourceException ex) {
                checkupsAvailable.put(i.getID(), i.getID());
            }
        }

        return checkupsAvailable;
    }

    /**
     * <p>
     * Getter for the field <code>startingLink</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStartingLink() {
        return startingLink;
    }

    /**
     * <p>
     * Setter for the field <code>startingLink</code>.</p>
     *
     * @param startingLink a {@link java.lang.String} object.
     */
    public void setStartingLink(String startingLink) {
        this.startingLink = startingLink;
    }

    /**
     * <p>
     * Getter for the field <code>desiredCheckups</code>.</p>
     *
     * @return an array of {@link java.lang.String} objects.
     */
    public String[] getDesiredCheckups() {
        return desiredCheckups;
    }

    /**
     * <p>
     * Setter for the field <code>desiredCheckups</code>.</p>
     *
     * @param desiredCheckups an array of {@link java.lang.String} objects.
     */
    public void setDesiredCheckups(String[] desiredCheckups) {
        this.desiredCheckups = desiredCheckups;
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
     * Getter for the field <code>domainsAllowed</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Domain> getDomainsAllowed() {
        return domainsAllowed;
    }

    /**
     * <p>
     * Setter for the field <code>domainsAllowed</code>.</p>
     *
     * @param domainsAllowed a {@link java.util.List} object.
     */
    public void setDomainsAllowed(List<Domain> domainsAllowed) {
        this.domainsAllowed = domainsAllowed;
    }

    /**
     * <p>
     * Getter for the field <code>maxCrawlingDepth</code>.</p>
     *
     * @return a int.
     */
    public int getMaxCrawlingDepth() {
        return maxCrawlingDepth;
    }

    /**
     * <p>
     * Setter for the field <code>maxCrawlingDepth</code>.</p>
     *
     * @param maxCrawlingDepth a int.
     */
    public void setMaxCrawlingDepth(int maxCrawlingDepth) {
        this.maxCrawlingDepth = maxCrawlingDepth;
    }

    /**
     * <p>
     * Getter for the field <code>minRequestInterval</code>.</p>
     *
     * @return a int.
     */
    public int getMinRequestInterval() {
        return minRequestInterval;
    }

    /**
     * <p>
     * Setter for the field <code>minRequestInterval</code>.</p>
     *
     * @param minRequestInterval a int.
     */
    public void setMinRequestInterval(int minRequestInterval) {
        this.minRequestInterval = minRequestInterval;
    }

    /**
     * <p>
     * Getter for the field <code>httpHeaders</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Header> getHttpHeaders() {
        return httpHeaders;
    }

    /**
     * <p>
     * Setter for the field <code>httpHeaders</code>.</p>
     *
     * @param httpHeaders a {@link java.util.List} object.
     */
    public void setHttpHeaders(List<Header> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    /**
     * <p>
     * Getter for the field <code>previousCheckupId</code>.</p>
     *
     * @return a int.
     */
    public int getPreviousCheckupId() {
        return previousCheckupId;
    }

    /**
     * <p>
     * Setter for the field <code>previousCheckupId</code>.</p>
     *
     * @param previousCheckupId a int.
     */
    public void setPreviousCheckupId(int previousCheckupId) {
        this.previousCheckupId = previousCheckupId;
    }

    /**
     * This action creates a new validation (it's started immediately after
     * creation)
     *
     * @return jsf view
     * @throws
     * org.presentation.presentation.exception.UserAuthorizationException if
     * any.
     * @throws java.net.MalformedURLException if any.
     */
    public String startValidation() throws UserAuthorizationException, MalformedURLException {

        CheckingRequest r = new CheckingRequest();
        OptionContainer oc = new OptionContainer();

        /*	
         if(this.desiredCheckups.length == 0) {
         this.addMessage(new FacesMessage(this.msg.getString("newCheckup.no_test_selected")));
         return "";
         }
         */
        // checkboxes - to be tested - definition by array should be perfect
        for (String option : this.desiredCheckups) {
            oc.addOption(option);
        }

        if (domainsAllowed.isEmpty()) {
            URL url = new URL(startingLink);
            domainsAllowed.add(new Domain(url.getHost()));
        }
        r.setAllowedDomains(domainsAllowed);

        r.setMaxDepth(maxCrawlingDepth);
        r.setRequestInterval(this.minRequestInterval);
        r.setPageLimit(pageLimit);
        r.setStartingPoint(new LinkURL(startingLink));
        r.setHeaders(httpHeaders);
        r.setChosenOptions(oc);

        checkRequestReceiver.addNewCheckingRequest(this.getLoggedUser().getEmail(), r);

        return "newCheckupAccepted?faces-redirect=true";

    }

    /**
     * This action loads params from previous checkup to the web-form
     *
     * @throws
     * org.presentation.presentation.exception.UserAuthorizationException if
     * any.
     */
    public void loadPreviousCheckup() throws UserAuthorizationException {
        if (this.previousCheckupId > 0) {
            Checkup c = this.persistance.findCheckup(this.previousCheckupId);
            if (c != null) {
                if (c.getUser() == null || !c.getUser().equals(this.getLoggedUser())) {
                    this.addMessage(new FacesMessage(msg.getString("common.checkup_not_yours")));
                    return;
                }

                this.setStartingLink(c.getStartPoint());
                this.setPageLimit(c.getPageLimit());
                this.setMinRequestInterval(c.getCheckingInterval());
                this.setMaxCrawlingDepth(c.getMaxDepth());

                List<Domain> domainList = this.persistance.findCheckupDomains(c);
                if (domainList != null) {
                    this.setDomainsAllowed(domainList);
                }

                List<Header> headerList = this.persistance.findCheckupHeaders(c);
                if (headerList != null) {
                    this.setHttpHeaders(headerList);
                }

                List<String> desiredCheckupIdList = new ArrayList<>();
                List<ChosenOption> optionList = this.persistance.findCheckupOptions(c);
                if (optionList != null) {
                    for (ChosenOption chosenOption : optionList) {
                        desiredCheckupIdList.add(chosenOption.getIdOption());
                    }
                    if (desiredCheckupIdList.size() > 0) {
                        this.setDesiredCheckups((String[]) desiredCheckupIdList.toArray(new String[0]));
                    }
                }

            }
        }
    }

}
