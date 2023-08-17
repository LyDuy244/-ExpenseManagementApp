/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupExpense.findAll", query = "SELECT g FROM GroupExpense g"),
    @NamedQuery(name = "GroupExpense.findById", query = "SELECT g FROM GroupExpense g WHERE g.id = :id"),
    @NamedQuery(name = "GroupExpense.findByName", query = "SELECT g FROM GroupExpense g WHERE g.name = :name"),
    @NamedQuery(name = "GroupExpense.findByTitle", query = "SELECT g FROM GroupExpense g WHERE g.title = :title"),
    @NamedQuery(name = "GroupExpense.findByDescription", query = "SELECT g FROM GroupExpense g WHERE g.description = :description"),
    @NamedQuery(name = "GroupExpense.findByStartDate", query = "SELECT g FROM GroupExpense g WHERE g.startDate = :startDate"),
    @NamedQuery(name = "GroupExpense.findByEndDate", query = "SELECT g FROM GroupExpense g WHERE g.endDate = :endDate"),
    @NamedQuery(name = "GroupExpense.findByTotalIncome", query = "SELECT g FROM GroupExpense g WHERE g.totalIncome = :totalIncome"),
    @NamedQuery(name = "GroupExpense.findByTotalExpense", query = "SELECT g FROM GroupExpense g WHERE g.totalExpense = :totalExpense"),
    @NamedQuery(name = "GroupExpense.findByIsActive", query = "SELECT g FROM GroupExpense g WHERE g.isActive = :isActive")})
public class GroupExpense implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_income")
    private Double totalIncome;
    @Column(name = "total_expense")
    private Double totalExpense;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_active")
    private boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Set<GroupTransaction> groupTransactionSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Set<GroupMember> groupMemberSet;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User ownerId;

    public GroupExpense() {
    }

    public GroupExpense(Integer id) {
        this.id = id;
    }

    public GroupExpense(Integer id, String name, String title, String description, Date startDate, Date endDate, boolean isActive) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public Set<GroupTransaction> getGroupTransactionSet() {
        return groupTransactionSet;
    }

    public void setGroupTransactionSet(Set<GroupTransaction> groupTransactionSet) {
        this.groupTransactionSet = groupTransactionSet;
    }

    @XmlTransient
    public Set<GroupMember> getGroupMemberSet() {
        return groupMemberSet;
    }

    public void setGroupMemberSet(Set<GroupMember> groupMemberSet) {
        this.groupMemberSet = groupMemberSet;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
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
        if (!(object instanceof GroupExpense)) {
            return false;
        }
        GroupExpense other = (GroupExpense) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lnnd.pojo.GroupExpense[ id=" + id + " ]";
    }

}
