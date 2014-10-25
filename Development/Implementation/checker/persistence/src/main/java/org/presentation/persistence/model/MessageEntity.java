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
 * Entity class representing the Message entity in JPA entity architecture.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
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
    @NamedQuery(name = "MessageEntity.findByCheckupId", query = "SELECT m FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId ORDER BY m.priority DESC"),
    @NamedQuery(name = "MessageEntity.findAllResourcesInCheckup", query = "SELECT DISTINCT m.resource FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId"),
    @NamedQuery(name = "MessageEntity.findAllInCheckupByResource", query = "SELECT m FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.resource = :resource ORDER BY m.priority DESC"),
    @NamedQuery(name = "MessageEntity.findAllInCheckupByResources", query = "SELECT m FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.resource IN (:resources) ORDER BY m.priority DESC"),
    @NamedQuery(name = "MessageEntity.findAllInCheckupByDiscriminators", query = "SELECT m FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.discriminator IN (:discriminators) ORDER BY m.priority DESC"),
    @NamedQuery(name = "MessageEntity.findAllInCheckupByResourcesDiscriminators", query = "SELECT m FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.resource IN (:resources) AND m.discriminator IN (:discriminators) ORDER BY m.priority DESC"),
    @NamedQuery(name = "MessageEntity.countCheckupMessages", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId"),
    @NamedQuery(name = "MessageEntity.countCheckupMessagesFromResource", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.resource IN (:resources)"),
    @NamedQuery(name = "MessageEntity.countCheckupMessagesByDiscriminators", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.discriminator IN (:discriminators)"),
    @NamedQuery(name = "MessageEntity.countCheckupMessagesByResourcesDiscriminators", query = "SELECT COUNT(m) FROM MessageEntity m WHERE m.checkup.idCheckup = :checkupId AND m.resource IN (:resources) AND m.discriminator IN (:discriminators)")})
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
    @Basic(optional = false)
    @Column(name = "priority")
    private Integer priority;
    @JoinColumn(name = "checkup", referencedColumnName = "id_checkup")
    @ManyToOne(optional = false)
    private Checkup checkup;

    /**
     * Non-parametric constructor required by JPA specification.
     */
    public MessageEntity() {
    }

    /**
     * Constructor with primary key.
     *
     * @param id Primary key of message entity
     */
    public MessageEntity(Integer id) {
        this.id = id;
    }

    /**
     * <p>
     * Constructor for MessageEntity.</p>
     *
     * @param id a {@link java.lang.Integer} object.
     * @param discriminator a {@link java.lang.String} object.
     * @param message a {@link java.lang.String} object.
     * @param page a {@link java.lang.String} object.
     * @param column a {@link java.lang.Integer} object.
     * @param row a {@link java.lang.Integer} object.
     */
    public MessageEntity(Integer id, String discriminator, String message, String page, Integer column, Integer row) {
        this.id = id;
        this.discriminator = discriminator;
        this.message = message;
        this.page = page;
        this.column = column;
        this.row = row;
    }

    /**
     * <p>
     * Getter for the field <code>id</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>
     * Setter for the field <code>id</code>.</p>
     *
     * @param id a {@link java.lang.Integer} object.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>
     * Getter for the field <code>discriminator</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDiscriminator() {
        return discriminator;
    }

    /**
     * <p>
     * Setter for the field <code>discriminator</code>.</p>
     *
     * @param discriminator a {@link java.lang.String} object.
     */
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    /**
     * <p>
     * Getter for the field <code>message</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>
     * Setter for the field <code>message</code>.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * <p>
     * Getter for the field <code>page</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPage() {
        return page;
    }

    /**
     * <p>
     * Setter for the field <code>page</code>.</p>
     *
     * @param page a {@link java.lang.String} object.
     */
    public void setPage(String page) {
        if (page.length() > 2048) {
            page = page.substring(0, 2048);
        }
        this.page = page;
    }

    /**
     * <p>
     * Getter for the field <code>column</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getColumn() {
        return column;
    }

    /**
     * <p>
     * Setter for the field <code>column</code>.</p>
     *
     * @param column a {@link java.lang.Integer} object.
     */
    public void setColumn(Integer column) {
        this.column = column;
    }

    /**
     * <p>
     * Getter for the field <code>row</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getRow() {
        return row;
    }

    /**
     * <p>
     * Setter for the field <code>row</code>.</p>
     *
     * @param row a {@link java.lang.Integer} object.
     */
    public void setRow(Integer row) {
        this.row = row;
    }

    /**
     * <p>
     * Getter for the field <code>errorCode</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * <p>
     * Setter for the field <code>errorCode</code>.</p>
     *
     * @param errorCode a {@link java.lang.Integer} object.
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * <p>
     * Getter for the field <code>resource</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getResource() {
        return resource;
    }

    /**
     * <p>
     * Setter for the field <code>resource</code>.</p>
     *
     * @param resource a {@link java.lang.String} object.
     */
    public void setResource(String resource) {
        this.resource = resource;
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
     * Getter for the field <code>priority</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * <p>
     * Setter for the field <code>priority</code>.</p>
     *
     * @param priority a {@link java.lang.Integer} object.
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageEntity)) {
            return false;
        }
        MessageEntity other = (MessageEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "test.Message[ id=" + id + " ]";
    }

    /**
     * Method converts {@link org.presentation.persistence.model.MessageEntity}
     * class to its mapping class (subclass)
     * {@link org.presentation.model.logging.Message} by using
     * {@link org.presentation.model.logging.MessageMapper} interface. Method
     * requires Java reflection api for instantiation of Message implementation
     * by using its full class name persisted in discriminator column.
     *
     * @param entity Entity class representing the Message in database model
     * @return Specific instance of implemented
     * {@link org.presentation.model.logging.Message} class; zero if it is not
     * possible to instantiate Message implementation by using java reflection
     */
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

    /**
     * Method converts implementation of
     * {@link org.presentation.model.logging.Message} class to its mapped entity
     * class.
     *
     * @param message Message implementation instance to be mapped as entity
     * {@link org.presentation.persistence.model.MessageEntity} class
     * @return MessageEntity class used for persisting into database using JPA
     * framework.
     */
    public static MessageEntity convert(Message message) {
        MessageEntity res = new MessageEntity();
        MessageMapper mapper = new MessageMapperImpl(res);
        message.setIntoMapper(mapper);
        return res;
    }

}
