package org.presentation.persistence.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class representing the Login entity in JPA entity architecture.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Entity
@Vetoed
@Table(name = "login")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l"),
    @NamedQuery(name = "Login.findByIdLogin", query = "SELECT l FROM Login l WHERE l.idLogin = :idLogin"),
    @NamedQuery(name = "Login.findByAddress", query = "SELECT l FROM Login l WHERE l.address = :address"),
    @NamedQuery(name = "Login.findByTime", query = "SELECT l FROM Login l WHERE l.time = :time"),
    @NamedQuery(name = "Login.findByUserEmail", query = "SELECT l FROM Login l WHERE l.user.email = :email ORDER BY l.time DESC")})
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_login")
    private Integer idLogin;
    @Basic(optional = false)
    @Column(name = "address", length = 50)
    private String address;
    @Basic(optional = false)
    @Column(name = "\"time\"")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @JoinColumn(name = "\"user\"", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private User user;

    /**
     * Non-parametrict constructor required by JPA specification.
     */
    public Login() {
    }

    /**
     * Constructor with primary key.
     *
     * @param idLogin Primary key of login entity class
     */
    public Login(Integer idLogin) {
        this.idLogin = idLogin;
    }

    /**
     * <p>
     * Getter for the field <code>idLogin</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    public Integer getIdLogin() {
        return idLogin;
    }

    /**
     * <p>
     * Setter for the field <code>idLogin</code>.</p>
     *
     * @param idLogin a {@link java.lang.Integer} object.
     */
    public void setIdLogin(Integer idLogin) {
        this.idLogin = idLogin;
    }

    /**
     * <p>
     * Getter for the field <code>address</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAddress() {
        return address;
    }

    /**
     * <p>
     * Setter for the field <code>address</code>.</p>
     *
     * @param address a {@link java.lang.String} object.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * <p>
     * Getter for the field <code>time</code>.</p>
     *
     * @return a {@link java.util.Date} object.
     */
    public Date getTime() {
        return time;
    }

    /**
     * <p>
     * Setter for the field <code>time</code>.</p>
     *
     * @param time a {@link java.util.Date} object.
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * <p>
     * Getter for the field <code>user</code>.</p>
     *
     * @return a {@link org.presentation.persistence.model.User} object.
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>
     * Setter for the field <code>user</code>.</p>
     *
     * @param user a {@link org.presentation.persistence.model.User} object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogin != null ? idLogin.hashCode() : 0);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        return !((this.idLogin == null && other.idLogin != null) || (this.idLogin != null && !this.idLogin.equals(other.idLogin)));
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "testicek.Login[ idLogin=" + idLogin + " ]";
    }

}
