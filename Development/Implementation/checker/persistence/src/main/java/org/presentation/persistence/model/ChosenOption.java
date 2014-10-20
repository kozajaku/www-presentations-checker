package org.presentation.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class representing the Option entity in JPA entity architecture.
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@Entity
@Vetoed
@Table(name = "\"option\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChosenOption.findAll", query = "SELECT o FROM ChosenOption o"),
    @NamedQuery(name = "ChosenOption.findAllInCheckup", query = "SELECT o FROM ChosenOption o WHERE EXISTS (SELECT h FROM o.checkupList h WHERE h.idCheckup = :checkupId)")})
public class ChosenOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id_option", length = 50)
    private String idOption;
    @ManyToMany(mappedBy = "optionList")
    private List<Checkup> checkupList = new ArrayList<>();

    /**
     * Non-parametric constructor required by JPA specification.
     */
    public ChosenOption() {
    }

    /**
     * Constructor with primary key, which is also option name.
     *
     * @param idOption Primary key and name of the option
     */
    public ChosenOption(String idOption) {
        this.idOption = idOption;
    }

    /**
     * <p>Getter for the field <code>idOption</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getIdOption() {
        return idOption;
    }

    /**
     * <p>Setter for the field <code>idOption</code>.</p>
     *
     * @param idOption a {@link java.lang.String} object.
     */
    public void setIdOption(String idOption) {
        this.idOption = idOption;
    }

    /**
     * <p>Getter for the field <code>checkupList</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    @XmlTransient
    public List<Checkup> getCheckupList() {
        return checkupList;
    }

    /**
     * <p>Setter for the field <code>checkupList</code>.</p>
     *
     * @param checkupList a {@link java.util.List} object.
     */
    public void setCheckupList(List<Checkup> checkupList) {
        this.checkupList = checkupList;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOption != null ? idOption.hashCode() : 0);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChosenOption)) {
            return false;
        }
        ChosenOption other = (ChosenOption) object;
        return !((this.idOption == null && other.idOption != null) || (this.idOption != null && !this.idOption.equals(other.idOption)));
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "test.Option[ idOption=" + idOption + " ]";
    }

    /**
     * Method converts {@link java.lang.String} representing name of option to
     * {@link org.presentation.persistence.model.ChosenOption} entity class.
     *
     * @param option String representing source of conversion and option name
     * @return Entity class possible to be persisted by JPA
     */
    public static ChosenOption convert(String option) {
        return new ChosenOption(option);
    }

    /**
     * Method converts {@link org.presentation.persistence.model.ChosenOption} entity class back to {@link java.lang.String}
     * class which serves as the option name.
     *
     * @param chOption Entity class as the source of conversion
     * @return String representing name (unique identifier) of the option
     */
    public static String convert(ChosenOption chOption) {
        return chOption.getIdOption();
    }
}
