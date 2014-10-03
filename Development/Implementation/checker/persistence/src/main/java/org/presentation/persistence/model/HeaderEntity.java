package org.presentation.persistence.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author radio.koza
 */
@Entity
@Table(name = "header")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HeaderEntity.findAll", query = "SELECT h FROM HeaderEntity h"),
    @NamedQuery(name = "HeaderEntity.findByIdHeader", query = "SELECT h FROM HeaderEntity h WHERE h.idHeader = :idHeader"),
    @NamedQuery(name = "HeaderEntity.findByKey", query = "SELECT h FROM HeaderEntity h WHERE h.key = :key"),
    @NamedQuery(name = "HeaderEntity.findByValue", query = "SELECT h FROM HeaderEntity h WHERE h.value = :value"),
    @NamedQuery(name = "HeaderEntity.findByCheckupId", query = "SELECT h FROM HeaderEntity h WHERE h.checkup.idCheckup = :checkupId")})
public class HeaderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_header")
    private Integer idHeader;
    @Basic(optional = false)
    @Column(name = "key", length = 100)
    private String key;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
    @JoinColumn(name = "checkup", referencedColumnName = "id_checkup")
    @ManyToOne(optional = false)
    private Checkup checkup;

    public HeaderEntity() {
    }

    public HeaderEntity(Integer idHeader) {
        this.idHeader = idHeader;
    }

    public HeaderEntity(Integer idHeader, String key, String value) {
        this.idHeader = idHeader;
        this.key = key;
        this.value = value;
    }

    public Integer getIdHeader() {
        return idHeader;
    }

    public void setIdHeader(Integer idHeader) {
        this.idHeader = idHeader;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Checkup getCheckup() {
        return checkup;
    }

    public void setCheckup(Checkup checkup) {
        this.checkup = checkup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHeader != null ? idHeader.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HeaderEntity)) {
            return false;
        }
        HeaderEntity other = (HeaderEntity) object;
        return !((this.idHeader == null && other.idHeader != null) || (this.idHeader != null && !this.idHeader.equals(other.idHeader)));
    }

    @Override
    public String toString() {
        return "test.Header[ idHeader=" + idHeader + " ]";
    }

}
