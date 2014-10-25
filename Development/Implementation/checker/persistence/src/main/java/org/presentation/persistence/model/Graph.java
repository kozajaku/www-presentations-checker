package org.presentation.persistence.model;

import java.io.Serializable;
import java.util.Objects;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class representing the Graph entity in JPA entity architecture.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Entity
@Vetoed
@Table(name = "graph")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Graph.findAll", query = "SELECT g FROM Graph g"),
    @NamedQuery(name = "Graph.findByCheckupId", query = "SELECT g FROM Graph g WHERE g.checkup.idCheckup = :checkupId"),
    @NamedQuery(name = "Graph.listGraphTypes", query = "SELECT DISTINCT g.graphType FROM Graph g WHERE g.checkup.idCheckup = :checkupId"),
    @NamedQuery(name = "Graph.findGraphByGraphType", query = "SELECT g FROM Graph g WHERE g.checkup.idCheckup = :checkupId AND g.graphType = :graphType")})
public class Graph implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_graph")
    private Integer idGraph;
    @Column(name = "\"output\"")
    @Lob
    private String output;
    @Column(name = "graph_type", length = 50)
    @Basic(optional = false)
    private String graphType;
    @JoinColumn(name = "checkup", referencedColumnName = "id_checkup")
    @ManyToOne(optional = false)
    private Checkup checkup;

    /**
     * Non-parametric constructor required by JPA specification.
     */
    public Graph() {
    }

    /**
     * Constructor with primary key.
     *
     * @param idGraph Primary key of the Graph entity class
     */
    public Graph(Integer idGraph) {
        this.idGraph = idGraph;
    }

    /**
     * <p>
     * Constructor for Graph.</p>
     *
     * @param output a {@link java.lang.String} object.
     * @param checkup a {@link org.presentation.persistence.model.Checkup}
     * object.
     */
    public Graph(String output, Checkup checkup) {
        this.output = output;
        this.checkup = checkup;
    }

    /**
     * <p>
     * Getter for the field <code>idGraph</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getIdGraph() {
        return idGraph;
    }

    /**
     * <p>
     * Setter for the field <code>idGraph</code>.</p>
     *
     * @param idGraph a {@link java.lang.Integer} object.
     */
    public void setIdGraph(Integer idGraph) {
        this.idGraph = idGraph;
    }

    /**
     * <p>
     * Getter for the field <code>output</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getOutput() {
        return output;
    }

    /**
     * <p>
     * Setter for the field <code>output</code>.</p>
     *
     * @param output a {@link java.lang.String} object.
     */
    public void setOutput(String output) {
        this.output = output;
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
     * Setter for the field <code>checkup</code>.</p>
     *
     * @param checkup a {@link org.presentation.persistence.model.Checkup}
     * object.
     */
    public void setCheckup(Checkup checkup) {
        this.checkup = checkup;
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

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGraph != null ? idGraph.hashCode() : 0);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Graph other = (Graph) obj;
        return Objects.equals(this.idGraph, other.idGraph);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Graph{" + "idGraph=" + idGraph + ", output=" + output + ", checkup=" + checkup + '}';
    }

}
