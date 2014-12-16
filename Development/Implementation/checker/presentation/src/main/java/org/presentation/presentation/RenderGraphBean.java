/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.Graph;
import org.presentation.persistence.model.User;
import org.presentation.presentation.exception.UserAuthorizationException;

/**
 * This bean brings the ability of graph rendering
 *
 * @author petrof
 * @version $Id: $Id
 */
@Named
@RequestScoped
public class RenderGraphBean extends ProtectedBean {

    @NotNull
    protected int checkupId;

    protected Checkup checkup;

    protected String graphType;

    protected Graph selectedGraph;

    /**
     * <p>
     * init.</p>
     */
    @PostConstruct
    public void init() {
        // set checkupId from GET argument if not set by JSF
        if (checkupId == 0) {
            Map<String, String> params = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap();
            String pCheckupId = params.get("checkupId");
            if (pCheckupId != null) {
                checkupId = Integer.parseInt(pCheckupId);
            }
        }
    }

    /**
     * This method loads the checkup by id. Checking/validation is also
     * involved.
     *
     * @return success
     */
    protected boolean loadCheckup() throws UserAuthorizationException {
        Checkup c = this.persistance.findCheckup(checkupId);

        this.checkup = c;

        if (c == null) {
            this.addMessage(new FacesMessage(msg.getString("common.checkup_not_found")));
            return false;
        }

        User user = checkup.getUser();
        if (user == null || !user.equals(this.getLoggedUser())) {
            this.checkup = null;
            this.addMessage(new FacesMessage(msg.getString("common.checkup_not_yours")));
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method decides if all variables necessary to display a specific
     * checkup are set
     *
     * @return are all necessary vars set?
     */
    protected boolean validateSpecificGraphIdVars() {
        if (this.checkup != null) {
            if (graphType != null && this.graphType.length() > 0) {
                return true;
            } else {
                this.addMessage(new FacesMessage(msg.getString("RenderGraph.graph_not_set")));
            }
        }
        return false;
    }

    /**
     * This action prepares the bean for graph rendering
     */
    public void showGraph() throws UserAuthorizationException {
        if (!this.loadCheckup()) {
            return;
        }
        if (this.validateSpecificGraphIdVars()) {
            this.selectedGraph = this.persistance.findGraphByGraphType(checkup, this.graphType);
        }
    }

    /**
     * This action prepares the bean for graph list rendering
     */
    public void showGraphList() throws UserAuthorizationException {
        if (!this.loadCheckup()) {
            return;
        }
    }

    /**
     * This action brings the ability to download graphs
     *
     * @throws java.io.IOException if any.
     */
    public void download() throws IOException, UserAuthorizationException {
        this.showGraph();
        if (this.selectedGraph != null) {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            ec.responseReset();
            ec.setResponseContentType("application/octet-stream");
            ec.setResponseContentLength(this.selectedGraph.getOutput().length());

            // very dirty solution, todo: for each graph result, store its content-type
            if (this.selectedGraph.getGraphType().contains("graphviz")) {
                ec.setResponseHeader("Content-Disposition", "attachment; filename=\"graph" + this.selectedGraph.getIdGraph() + ".gv.txt\"");
            } else {
                ec.setResponseHeader("Content-Disposition", "attachment; filename=\"graph" + this.selectedGraph.getIdGraph() + ".html\"");
            }

            OutputStream output = ec.getResponseOutputStream();
            output.write(this.selectedGraph.getOutput().getBytes());

            fc.responseComplete();
        }
    }

    /**
     * <p>
     * Getter for the field <code>checkupId</code>.</p>
     *
     * @return a int.
     */
    public int getCheckupId() {
        return checkupId;
    }

    /**
     * <p>
     * Setter for the field <code>checkupId</code>.</p>
     *
     * @param checkupId a int.
     */
    public void setCheckupId(int checkupId) {
        this.checkupId = checkupId;
    }

    /**
     * <p>
     * Getter for the field <code>graphType</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getGraphType() {
        return graphType;
    }

    /**
     * <p>
     * Setter for the field <code>graphType</code>.</p>
     *
     * @param graphType a {@link java.lang.String} object.
     */
    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    /**
     * <p>
     * Getter for the field <code>selectedGraph</code>.</p>
     *
     * @return a {@link org.presentation.persistence.model.Graph} object.
     */
    public Graph getSelectedGraph() {
        return selectedGraph;
    }

    /**
     * <p>
     * Getter for the field <code>checkup</code>.</p>
     *
     * @return a {@link org.presentation.persistence.model.Checkup} object.
     */
    public Checkup getCheckup() {
        return checkup;
    }

    /**
     * <p>
     * getAvailableGraphs.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Graph> getAvailableGraphs() throws UserAuthorizationException {
        if (this.checkup == null) {
            this.loadCheckup();
        }
        if (this.checkup != null) {
            return this.persistance.findCheckupGraphs(checkup);
        } else {
            return null;
        }
    }

}
