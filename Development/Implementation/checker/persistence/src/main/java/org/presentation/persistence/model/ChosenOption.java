package org.presentation.persistence.model;

import java.io.Serializable;
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
 *
 * @author radio.koza
 */
@Entity
@Vetoed
@Table(name = "option")
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
    private List<Checkup> checkupList;

    public ChosenOption() {
    }

    public ChosenOption(String idOption) {
        this.idOption = idOption;
    }

    public String getIdOption() {
        return idOption;
    }

    public void setIdOption(String idOption) {
        this.idOption = idOption;
    }

    @XmlTransient
    public List<Checkup> getCheckupList() {
        return checkupList;
    }

    public void setCheckupList(List<Checkup> checkupList) {
        this.checkupList = checkupList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOption != null ? idOption.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChosenOption)) {
            return false;
        }
        ChosenOption other = (ChosenOption) object;
        return !((this.idOption == null && other.idOption != null) || (this.idOption != null && !this.idOption.equals(other.idOption)));
    }

    @Override
    public String toString() {
        return "test.Option[ idOption=" + idOption + " ]";
    }

    public static ChosenOption convert(String option) {
        return new ChosenOption(option);
    }

    public static String convert(ChosenOption chOption) {
        return chOption.getIdOption();
    }
}
