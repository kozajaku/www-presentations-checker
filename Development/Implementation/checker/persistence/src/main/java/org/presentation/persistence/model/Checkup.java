package org.presentation.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.inject.Vetoed;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "checkup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Checkup.findAll", query = "SELECT c FROM Checkup c"),
    @NamedQuery(name = "Checkup.findByIdCheckup", query = "SELECT c FROM Checkup c WHERE c.idCheckup = :idCheckup"),
    @NamedQuery(name = "Checkup.findByCheckingCreated", query = "SELECT c FROM Checkup c WHERE c.checkingCreated = :checkingCreated"),
    @NamedQuery(name = "Checkup.findByState", query = "SELECT c FROM Checkup c WHERE c.state = :state"),
    @NamedQuery(name = "Checkup.findByCheckingFinished", query = "SELECT c FROM Checkup c WHERE c.checkingFinished = :checkingFinished"),
    @NamedQuery(name = "Checkup.findByStartPoint", query = "SELECT c FROM Checkup c WHERE c.startPoint = :startPoint"),
    @NamedQuery(name = "Checkup.findByMaxDepth", query = "SELECT c FROM Checkup c WHERE c.maxDepth = :maxDepth"),
    @NamedQuery(name = "Checkup.findByUserEmail", query = "SELECT c FROM Checkup c WHERE c.user.email = :email ORDER BY c.checkingCreated DESC")})
public class Checkup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_checkup")
    private Integer idCheckup;
    @Basic(optional = false)
    @Column(name = "checking_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkingCreated;
    @Basic(optional = false)
    @Column(name = "\"state\"")
    @Enumerated(EnumType.STRING)
    private CheckState state;
    @Column(name = "checking_finished")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkingFinished;
    @Basic(optional = false)
    @Column(name = "start_point", length = 255)
    private String startPoint;
    @Column(name = "max_depth")
    private Integer maxDepth;
    @Column(name = "checking_interval")
    private Integer checkingInterval;
    @Column(name = "page_limit")
    private Integer pageLimit;
    @JoinTable(name = "checkup_has_option", joinColumns = {
        @JoinColumn(name = "checkup", referencedColumnName = "id_checkup")}, inverseJoinColumns = {
        @JoinColumn(name = "\"option\"", referencedColumnName = "id_option")})
    @ManyToMany
    private List<ChosenOption> optionList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkup")
    private List<MessageEntity> messageEntityList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checking")
    private List<Domain> domainList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkup")
    private List<HeaderEntity> headerList = new ArrayList<>();
    @JoinColumn(name = "user", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkup")
    private List<Graph> graphList = new ArrayList<>();

    public Checkup() {
    }

    public Checkup(Integer idCheckup) {
        this.idCheckup = idCheckup;
    }

    public Integer getIdCheckup() {
        return idCheckup;
    }

    public void setIdCheckup(Integer idCheckup) {
        this.idCheckup = idCheckup;
    }

    public Date getCheckingCreated() {
        return checkingCreated;
    }

    public void setCheckingCreated(Date checkingCreated) {
        this.checkingCreated = checkingCreated;
    }

    public CheckState getState() {
        return state;
    }

    public void setState(CheckState state) {
        this.state = state;
    }

    public Date getCheckingFinished() {
        return checkingFinished;
    }

    public void setCheckingFinished(Date checkingFinished) {
        this.checkingFinished = checkingFinished;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public Integer getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(Integer maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Integer getCheckingInterval() {
        return checkingInterval;
    }

    public void setCheckingInterval(Integer checkingInterval) {
        this.checkingInterval = checkingInterval;
    }

    @XmlTransient
    public List<ChosenOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<ChosenOption> optionList) {
        this.optionList = optionList;
    }

    @XmlTransient
    public List<MessageEntity> getMessageEntityList() {
        return messageEntityList;
    }

    public void setResourceList(List<MessageEntity> messageEntityList) {
        this.messageEntityList = messageEntityList;
    }

    @XmlTransient
    public List<Domain> getDomainList() {
        return domainList;
    }

    public void setDomainList(List<Domain> domainList) {
        this.domainList = domainList;
    }

    @XmlTransient
    public List<HeaderEntity> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<HeaderEntity> headerList) {
        this.headerList = headerList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlTransient
    public List<Graph> getGraphList() {
        return graphList;
    }

    public void setGraphList(List<Graph> graphList) {
        this.graphList = graphList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCheckup != null ? idCheckup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Checkup)) {
            return false;
        }
        Checkup other = (Checkup) object;
        return !((this.idCheckup == null && other.idCheckup != null) || (this.idCheckup != null && !this.idCheckup.equals(other.idCheckup)));
    }

    @Override
    public String toString() {
        return "test.Checkup[ idCheckup=" + idCheckup + " ]";
    }

    public Integer getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(Integer pageLimit) {
        this.pageLimit = pageLimit;
    }

}
