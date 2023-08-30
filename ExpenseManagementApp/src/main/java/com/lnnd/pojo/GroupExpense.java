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
@Table(name = "group_expense")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupExpense.findAll", query = "SELECT g FROM GroupExpense g"),
    @NamedQuery(name = "GroupExpense.findById", query = "SELECT g FROM GroupExpense g WHERE g.id = :id"),
    @NamedQuery(name = "GroupExpense.findByName", query = "SELECT g FROM GroupExpense g WHERE g.name = :name"),
    @NamedQuery(name = "GroupExpense.findByTitle", query = "SELECT g FROM GroupExpense g WHERE g.title = :title"),
    @NamedQuery(name = "GroupExpense.findByDescription", query = "SELECT g FROM GroupExpense g WHERE g.description = :description"),
    @NamedQuery(name = "GroupExpense.findByStartDate", query = "SELECT g FROM GroupExpense g WHERE g.startDate = :startDate"),
    @NamedQuery(name = "GroupExpense.findByEndDate", query = "SELECT g FROM GroupExpense g WHERE g.endDate = :endDate"),
    @NamedQuery(name = "GroupExpense.findByIsActive", query = "SELECT g FROM GroupExpense g WHERE g.isActive = :isActive")})
public class GroupExpense implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message = "{groupExpense.name.notNull}")
    @Size(min = 1, max = 50, message = "{groupExpense.name.lenErr}")
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull(message = "{groupExpense.purpose.notNull}")
    @Size(min = 1, max = 50, message = "{groupExpense.purpose.lenErr}")
    @Column(name = "title")
    private String title;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull(message = "{groupExpense.startDate.notNull}")
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Basic(optional = false)
    @NotNull(message = "{groupExpense.endDate.notNull}")
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "is_active")
    private boolean isActive;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Set<GroupTransaction> groupTransactionSet;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User ownerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Set<GroupMember> groupMemberSet;

    public GroupExpense() {
    }

    public GroupExpense(Integer id) {
        this.id = id;
    }

    public GroupExpense(Integer id, String name, String title, Date startDate, Date endDate, boolean isActive) {
        this.id = id;
        this.name = name;
        this.title = title;
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

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }

    @XmlTransient
    public Set<GroupMember> getGroupMemberSet() {
        return groupMemberSet;
    }

    public void setGroupMemberSet(Set<GroupMember> groupMemberSet) {
        this.groupMemberSet = groupMemberSet;
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

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
