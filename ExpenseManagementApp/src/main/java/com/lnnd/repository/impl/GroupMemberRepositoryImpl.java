/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.GroupMember;
import com.lnnd.pojo.GroupTransaction;
import com.lnnd.repository.GroupMemberRepository;
import com.lnnd.service.GroupTransactionService;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
public class GroupMemberRepositoryImpl implements GroupMemberRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean addGroupMember(GroupMember gm) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(gm);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public GroupMember getGroupMemberByUserIdAndGroupId(int userId, int groupId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<GroupMember> query = b.createQuery(GroupMember.class);
        Root<GroupMember> root = query.from(GroupMember.class);

        Predicate userIdPredicate = b.equal(root.get("userId"), userId);
        Predicate groupIdPredicate = b.equal(root.get("groupId"), groupId);
        Predicate finalPredicate = b.and(userIdPredicate, groupIdPredicate);

        query.select(root).where(finalPredicate);

        Query q = session.createQuery(query);
        List<GroupMember> grMemberList = q.getResultList();

        if (!grMemberList.isEmpty()) {
            return grMemberList.get(0);
        } else {
            return null; // Trả về null khi không có đối tượng
        }
    }

    @Override
    public List<Object[]> getGroupMembersExpensesByGroupId(int groupId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        Root<GroupMember> groupMemberRoot = query.from(GroupMember.class);
        Join<GroupMember, GroupExpense> groupExpenseJoin = groupMemberRoot.join("groupId");
        Join<GroupMember, GroupTransaction> groupTransactionJoin = groupMemberRoot.join("groupTransactionSet", JoinType.LEFT);

        Expression<Double> sumThu = builder.sum(
                builder.<Double>selectCase()
                        .when(builder.and(builder.equal(groupTransactionJoin.get("typeId"), 1), builder.equal(groupTransactionJoin.get("isActive"), 1)), groupTransactionJoin.<Double>get("amount"))
                        .otherwise(0.0)
        );

        Expression<Double> sumChi = builder.sum(
                builder.<Double>selectCase()
                        .when(builder.and(builder.equal(groupTransactionJoin.get("typeId"), 2), builder.equal(groupTransactionJoin.get("isActive"), 1)), groupTransactionJoin.<Double>get("amount"))
                        .otherwise(0.0)
        );

        query.multiselect(
              
                groupMemberRoot.get("userId"),
                sumThu.alias("Thu"),
                sumChi.alias("Chi")
        );

        query.where(
                builder.equal(groupExpenseJoin.get("id"), groupId)
        );

        query.groupBy(
                
                groupMemberRoot.get("userId")
        );

        TypedQuery<Object[]> typedQuery = session.createQuery(query);
        return typedQuery.getResultList();
    }

}
