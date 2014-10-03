package org.presentation.persistence.model;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author radio.koza
 */
@Entity
@Vetoed
@Table(name = "resource")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r"),
    @NamedQuery(name = "Resource.findByName", query = "SELECT r FROM Resource r WHERE r.name = :name")})
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name", length = 50)
    private String name;
    @JoinColumn(name = "checkup", referencedColumnName = "id_checkup")
    @ManyToOne(optional = false)
    private Checkup checkup;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource")
    private List<MessageEntity> messageList;

    public Resource() {
    }

    public Resource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Checkup getCheckup() {
        return checkup;
    }

    public void setCheckup(Checkup checkup) {
        this.checkup = checkup;
    }

    @XmlTransient
    public List<MessageEntity> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageEntity> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource other = (Resource) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Resource[ name=" + name + " ]";
    }
}
