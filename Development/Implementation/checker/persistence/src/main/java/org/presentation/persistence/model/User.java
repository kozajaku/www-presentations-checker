package org.presentation.persistence.model;

import java.io.Serializable;
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
 *
 * @author radio.koza
 */
@Entity
@Vetoed
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),    
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
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
    private List<Login> loginList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Checkup> checkupList;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @XmlTransient
    public List<Login> getLoginList() {
        return loginList;
    }

    public void setLoginList(List<Login> loginList) {
        this.loginList = loginList;
    }

    @XmlTransient
    public List<Checkup> getCheckupList() {
        return checkupList;
    }

    public void setCheckupList(List<Checkup> checkupList) {
        this.checkupList = checkupList;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", name=" + name + ", surname=" + surname + ", registrationDate=" + registrationDate + '}';
    }

    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
    
    

}
