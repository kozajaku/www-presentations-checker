package org.presentation.persistence.model;

import java.io.Serializable;
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
import org.presentation.model.Header;

/**
 * Entity class representing the Header entity in JPA entity architecture.
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_header")
    private Integer idHeader;
    @Basic(optional = false)
    @Column(name = "\"key\"", length = 100)
    private String key;
    @Basic(optional = false)
    @Column(name = "\"value\"")
    private String value;
    @JoinColumn(name = "checkup", referencedColumnName = "id_checkup")
    @ManyToOne(optional = false)
    private Checkup checkup;

    /**
     * Non-parametric constructor required by JPA specification.
     */
    public HeaderEntity() {
    }

    /**
     * Constructor with primary key.
     *
     * @param idHeader Primary key of header entity class
     */
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

    /**
     * Method converts {@link HeaderEntity} class to its mapping class
     * {@link Header}.
     *
     * @param headerEntity Entity class as the source of conversion
     * @return Model class {@link Header} as the target of conversion
     */
    public static Header convert(HeaderEntity headerEntity) {
        return new Header(headerEntity.getKey(), headerEntity.getValue());
    }

    /**
     * Method converts {@link Header} class to is mapped entity class
     * {@link HeaderEntity}.
     *
     * @param header Model class @{link Header} as the source of the conversion
     * @return HeaderEntity class as the target of conversion
     */
    public static HeaderEntity convert(Header header) {
        HeaderEntity ent = new HeaderEntity();
        ent.setKey(header.getKey());
        ent.setValue(header.getValue());
        return ent;
    }

}
