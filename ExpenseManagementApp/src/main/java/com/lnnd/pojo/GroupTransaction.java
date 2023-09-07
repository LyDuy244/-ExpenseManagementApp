/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.pojo;

import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "group_transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupTransaction.findAll", query = "SELECT g FROM GroupTransaction g"),
    @NamedQuery(name = "GroupTransaction.findById", query = "SELECT g FROM GroupTransaction g WHERE g.id = :id"),
    @NamedQuery(name = "GroupTransaction.findByPurpose", query = "SELECT g FROM GroupTransaction g WHERE g.purpose = :purpose"),
    @NamedQuery(name = "GroupTransaction.findByDescription", query = "SELECT g FROM GroupTransaction g WHERE g.description = :description"),
    @NamedQuery(name = "GroupTransaction.findByAmount", query = "SELECT g FROM GroupTransaction g WHERE g.amount = :amount"),
    @NamedQuery(name = "GroupTransaction.findByCreatedDate", query = "SELECT g FROM GroupTransaction g WHERE g.createdDate = :createdDate"),
    @NamedQuery(name = "GroupTransaction.findByIsActive", query = "SELECT g FROM GroupTransaction g WHERE g.isActive = :isActive")})
public class GroupTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message = "{groupTransaction.purpose.notNull}")
    @Size(min = 1, max = 50, message = "{groupTransaction.purpose.lenErr}")
    @Column(name = "purpose")
    private String purpose;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private double amount;
    @Basic(optional = false)
    @NotNull(message = "{groupTransaction.createdDate.notNull}")
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "is_active")
    private boolean isActive;
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GroupExpense groupId;
    @JoinColumn(name = "group_member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GroupMember groupMemberId;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TypeTransaction typeId;

    public GroupTransaction() {
    }

    public GroupTransaction(Integer id) {
        this.id = id;
    }

    public GroupTransaction(Integer id, String purpose, double amount, Date createdDate, boolean isActive) {
        this.id = id;
        this.purpose = purpose;
        this.amount = amount;
        this.createdDate = createdDate;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public GroupExpense getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupExpense groupId) {
        this.groupId = groupId;
    }

    public GroupMember getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(GroupMember groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public TypeTransaction getTypeId() {
        return typeId;
    }

    public void setTypeId(TypeTransaction typeId) {
        this.typeId = typeId;
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
        if (!(object instanceof GroupTransaction)) {
            return false;
        }
        GroupTransaction other = (GroupTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lnnd.pojo.GroupTransaction[ id=" + id + " ]";
    }
    
}
