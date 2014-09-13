package org.presentation.checker.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Checkup.findByCheckHTML", query = "SELECT c FROM Checkup c WHERE c.checkHTML = :checkHTML"),
    @NamedQuery(name = "Checkup.findByCheckCSS", query = "SELECT c FROM Checkup c WHERE c.checkCSS = :checkCSS"),
    @NamedQuery(name = "Checkup.findByCheckCSSredundancy", query = "SELECT c FROM Checkup c WHERE c.checkCSSredundancy = :checkCSSredundancy"),
    @NamedQuery(name = "Checkup.findByCheckLinks", query = "SELECT c FROM Checkup c WHERE c.checkLinks = :checkLinks"),
    @NamedQuery(name = "Checkup.findByUserId", query = "SELECT c FROM Checkup c WHERE c.user.idUser = :userId ORDER BY c.checkingCreated DESC")})
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
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private CheckState state;
    @Lob
    @Column(name = "result_log")
    private String resultLog;
    @Lob
    @Column(name = "graph")
    private String graph;
    @Column(name = "checking_finished")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkingFinished;
    @Basic(optional = false)
    @Column(name = "start_point", length = 255)
    private String startPoint;
    @Column(name = "max_depth")
    private Integer maxDepth;
    @Basic(optional = false)
    @Column(name = "check_HTML")
    private boolean checkHTML;
    @Basic(optional = false)
    @Column(name = "check_CSS")
    private boolean checkCSS;
    @Basic(optional = false)
    @Column(name = "check_CSS_redundancy")
    private boolean checkCSSredundancy;
    @Basic(optional = false)
    @Column(name = "check_links")
    private boolean checkLinks;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checking")
    private List<Domain> domainList;
    @JoinColumn(name = "user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User user;

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

    public String getResultLog() {
        return resultLog;
    }

    public void setResultLog(String resultLog) {
        this.resultLog = resultLog;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
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

    public boolean getCheckHTML() {
        return checkHTML;
    }

    public void setCheckHTML(boolean checkHTML) {
        this.checkHTML = checkHTML;
    }

    public boolean getCheckCSS() {
        return checkCSS;
    }

    public void setCheckCSS(boolean checkCSS) {
        this.checkCSS = checkCSS;
    }

    public boolean getCheckCSSredundancy() {
        return checkCSSredundancy;
    }

    public void setCheckCSSredundancy(boolean checkCSSredundancy) {
        this.checkCSSredundancy = checkCSSredundancy;
    }

    public boolean getCheckLinks() {
        return checkLinks;
    }

    public void setCheckLinks(boolean checkLinks) {
        this.checkLinks = checkLinks;
    }

    @XmlTransient
    public List<Domain> getDomainList() {
        return domainList;
    }

    public void setDomainList(List<Domain> domainList) {
        this.domainList = domainList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCheckup != null ? idCheckup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Checkup)) {
            return false;
        }
        Checkup other = (Checkup) object;
        return !((this.idCheckup == null && other.idCheckup != null) || (this.idCheckup != null && !this.idCheckup.equals(other.idCheckup)));
    }

    @Override
    public String toString() {
        return "testicek.Checkup[ idCheckup=" + idCheckup + " ]";
    }

}
