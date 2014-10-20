package org.presentation.persistence.model;

import java.io.Serializable;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class representing the Domain entity in JPA entity architecture.
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@Entity
@Vetoed
@Table(name = "\"domain\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Domain.findAll", query = "SELECT d FROM Domain d"),
    @NamedQuery(name = "Domain.findByIdDomain", query = "SELECT d FROM Domain d WHERE d.idDomain = :idDomain"),
    @NamedQuery(name = "Domain.findByName", query = "SELECT d FROM Domain d WHERE d.name = :name"),
    @NamedQuery(name = "Domain.findByCheckupId", query = "SELECT d FROM Domain d WHERE d.checking.idCheckup = :checkupId")})
public class Domain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_domain")
    private Integer idDomain;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "checking", referencedColumnName = "id_checkup")
    @ManyToOne(optional = false)
    private Checkup checking;

    /**
     * Non-parametric constructor required by JPA specification.
     */
    public Domain() {
    }

    /**
     * Constructor with primary key.
     *
     * @param idDomain Primary key of Domain entity.
     */
    public Domain(Integer idDomain) {
        this.idDomain = idDomain;
    }

    /**
     * <p>Constructor for Domain.</p>
     *
     * @param idDomain a {@link java.lang.Integer} object.
     * @param name a {@link java.lang.String} object.
     */
    public Domain(Integer idDomain, String name) {
        this.idDomain = idDomain;
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>idDomain</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getIdDomain() {
        return idDomain;
    }

    /**
     * <p>Setter for the field <code>idDomain</code>.</p>
     *
     * @param idDomain a {@link java.lang.Integer} object.
     */
    public void setIdDomain(Integer idDomain) {
        this.idDomain = idDomain;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>checking</code>.</p>
     *
     * @return a {@link org.presentation.persistence.model.Checkup} object.
     */
    public Checkup getChecking() {
        return checking;
    }

    /**
     * <p>Setter for the field <code>checking</code>.</p>
     *
     * @param checking a {@link org.presentation.persistence.model.Checkup} object.
     */
    public void setChecking(Checkup checking) {
        this.checking = checking;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDomain != null ? idDomain.hashCode() : 0);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Domain)) {
            return false;
        }
        Domain other = (Domain) object;
        return !((this.idDomain == null && other.idDomain != null) || (this.idDomain != null && !this.idDomain.equals(other.idDomain)));
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "testicek.Domain[ idDomain=" + idDomain + " ]";
    }

    /**
     * Method converts {@link org.presentation.model.Domain} class to its mapped
     * entity class {@link org.presentation.persistence.model.Domain}.
     *
     * @param domain {@link org.presentation.model.Domain} model class as the
     * source of conversion
     * @return {@link org.presentation.persistence.model.Domain} as the target of conversion
     */
    public static Domain convert(org.presentation.model.Domain domain) {
        Domain dom = new Domain();
        dom.setName(domain.getDomain());
        return dom;
    }

    /**
     * Method converts {@link org.presentation.persistence.model.Domain} entity class to its mapping
     * {@link org.presentation.model.Domain} model class.
     *
     * @param domain {@link org.presentation.persistence.model.Domain} entity class as the source of conversion
     * @return {@link org.presentation.model.Domain} model class as the target
     * of conversion
     */
    public static org.presentation.model.Domain convert(Domain domain) {
        return new org.presentation.model.Domain(domain.getName());
    }

}
