package org.presentation.persistence.model;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.presentation.model.logging.Message;
import org.presentation.model.logging.MessageMapper;
import org.presentation.persistence.utils.MessageMapperImpl;

/**
 *
 * @author radio.koza
 */
@Entity
@Vetoed
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageEntity.findAll", query = "SELECT m FROM MessageEntity m"),
    @NamedQuery(name = "MessageEntity.findById", query = "SELECT m FROM MessageEntity m WHERE m.id = :id"),
    @NamedQuery(name = "MessageEntity.findByDiscriminator", query = "SELECT m FROM MessageEntity m WHERE m.discriminator = :discriminator"),
    @NamedQuery(name = "MessageEntity.findByPage", query = "SELECT m FROM MessageEntity m WHERE m.page = :page"),
    @NamedQuery(name = "MessageEntity.findByColumn", query = "SELECT m FROM MessageEntity m WHERE m.column = :column"),
    @NamedQuery(name = "MessageEntity.findByRow", query = "SELECT m FROM MessageEntity m WHERE m.row = :row"),
    @NamedQuery(name = "MessageEntity.findByErrorCode", query = "SELECT m FROM MessageEntity m WHERE m.errorCode = :errorCode"),
    @NamedQuery(name = "MessageEntity.findByCheckupId", query = "SELECT m FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId ORDER BY m.discriminator"),
    @NamedQuery(name = "MessageEntity.findAllResourcesInCheckup", query = "SELECT DISTINCT m.resource FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId"),
    @NamedQuery(name = "MessageEntity.findAllInCheckupByResource", query = "SELECT m FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.resource = :resource ORDER BY m.discriminator"),
    @NamedQuery(name = "MessageEntity.findAllInCheckupByResources", query = "SELECT m FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.resource IN (:resources) ORDER BY m.discriminator"),
    @NamedQuery(name = "MessageEntity.countCheckupMessages", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId"),
    @NamedQuery(name = "MessageEntity.countCheckupMessagesFromResource", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.resource IN (:resources)")})
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "discriminator", length = 200)
    private String discriminator;
    @Basic(optional = false)
    @Lob
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @Column(name = "page", length = 2048)
    private String page;
    @Column(name = "\"column\"")
    private Integer column;
    @Column(name = "\"row\"")
    private Integer row;
    @Column(name = "error_code")
    private Integer errorCode;
    @Basic(optional = false)
    @Column(name = "resource", length = 50)
    private String resource;
    @JoinColumn(name = "checkup", referencedColumnName = "id_checkup")
    @ManyToOne(optional = false)
    private Checkup checkup;

    public MessageEntity() {
    }

    public MessageEntity(Integer id) {
        this.id = id;
    }

    public MessageEntity(Integer id, String discriminator, String message, String page, Integer column, Integer row) {
        this.id = id;
        this.discriminator = discriminator;
        this.message = message;
        this.page = page;
        this.column = column;
        this.row = row;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        if (page.length() > 2048){
            page = page.substring(0, 2048);
        }
        this.page = page;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageEntity)) {
            return false;
        }
        MessageEntity other = (MessageEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "test.Message[ id=" + id + " ]";
    }

    public static Message convert(MessageEntity entity) {
        MessageMapper mapper = new MessageMapperImpl(entity);
        try {
            Class<?> res = Class.forName(entity.getDiscriminator());
            Message message = (Message) res.newInstance();
            message.setFromMapper(mapper);
            return message;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MessageEntity.class.getName()).log(Level.SEVERE, "Unable to dynamically load Message class with specified discriminator", ex);
            return null;
        }
    }

    public static MessageEntity convert(Message message) {
        MessageEntity res = new MessageEntity();
        MessageMapper mapper = new MessageMapperImpl(res);
        message.setIntoMapper(mapper);
        return res;
    }

}
