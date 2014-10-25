package org.presentation.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity class representing the entity User in JPA entity architecture.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Entity
@Vetoed
@Table(name = "\"user\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findBySalt", query = "SELECT u FROM User u WHERE u.salt = :salt"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findBySurname", query = "SELECT u FROM User u WHERE u.surname = :surname"),
    @NamedQuery(name = "User.findByRegistrationDate", query = "SELECT u FROM User u WHERE u.registrationDate = :registrationDate")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "email", unique = true, length = 254)
    private String email;
    @Basic(optional = false)
    @Column(name = "password", length = 64)
    private String password;
    @Basic(optional = false)
    @Column(name = "salt", length = 64)
    private String salt;
    @Basic(optional = false)
    @Column(name = "name", length = 100)
    private String name;
    @Basic(optional = false)
    @Column(name = "surname", length = 100)
    private String surname;
    @Basic(optional = false)
    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Login> loginList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Checkup> checkupList = new ArrayList<>();

    /**
     * Non parametric constructor - required by JPA specification.
     */
    public User() {
    }

    /**
     * Constructor taking email (primary key) of the user entity.
     *
     * @param email Email string of the user.
     */
    public User(String email) {
        this.email = email;
    }

    /**
     * <p>
     * Getter for the field <code>email</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>
     * Setter for the field <code>email</code>.</p>
     *
     * @param email a {@link java.lang.String} object.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>
     * Getter for the field <code>password</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @XmlTransient
    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * Setter for the field <code>password</code>.</p>
     *
     * @param password a {@link java.lang.String} object.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * Getter for the field <code>salt</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @XmlTransient
    public String getSalt() {
        return salt;
    }

    /**
     * <p>
     * Setter for the field <code>salt</code>.</p>
     *
     * @param salt a {@link java.lang.String} object.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * <p>
     * Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Getter for the field <code>surname</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * <p>
     * Setter for the field <code>surname</code>.</p>
     *
     * @param surname a {@link java.lang.String} object.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * <p>
     * Getter for the field <code>registrationDate</code>.</p>
     *
     * @return a {@link java.util.Date} object.
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * <p>
     * Setter for the field <code>registrationDate</code>.</p>
     *
     * @param registrationDate a {@link java.util.Date} object.
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * <p>
     * Getter for the field <code>loginList</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    @XmlTransient
    public List<Login> getLoginList() {
        return loginList;
    }

    /**
     * <p>
     * Setter for the field <code>loginList</code>.</p>
     *
     * @param loginList a {@link java.util.List} object.
     */
    public void setLoginList(List<Login> loginList) {
        this.loginList = loginList;
    }

    /**
     * <p>
     * Getter for the field <code>checkupList</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    @XmlTransient
    public List<Checkup> getCheckupList() {
        return checkupList;
    }

    /**
     * <p>
     * Setter for the field <code>checkupList</code>.</p>
     *
     * @param checkupList a {@link java.util.List} object.
     */
    public void setCheckupList(List<Checkup> checkupList) {
        this.checkupList = checkupList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "User{" + "email=" + email + ", name=" + name + ", surname=" + surname + ", registrationDate=" + registrationDate + '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.email);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.email, other.email);
    }

}
