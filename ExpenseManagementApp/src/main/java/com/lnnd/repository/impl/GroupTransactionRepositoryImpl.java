/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.GroupTransaction;
import com.lnnd.repository.GroupTransactionRepository;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class GroupTransactionRepositoryImpl implements GroupTransactionRepository {

    @Autowired
    private Environment env;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean addGroupTransaction(GroupTransaction grTransaction) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(grTransaction);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public List<GroupTransaction> getGroupTransactionByGroupId(int groupId, boolean accept, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<GroupTransaction> query = b.createQuery(GroupTransaction.class);
        Root<GroupTransaction> root = query.from(GroupTransaction.class);

        Predicate groupIdPredicate = b.equal(root.get("groupId"), groupId);
        Predicate activePredicate = null;
        if (accept == true) {
            activePredicate = b.equal(root.get("isActive"), 1);
        } else {
            activePredicate = b.equal(root.get("isActive"), 0);
        }

        Predicate finalPredicate = b.and(activePredicate, groupIdPredicate);

        query.select(root).where(finalPredicate);

        Query q = session.createQuery(query);

        int ps = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

        if (params != null) {
            String pageNoAccept = params.get("pageNoAccept");
            if (pageNoAccept != null && !pageNoAccept.isEmpty() && accept == false) {
                int p = Integer.parseInt(pageNoAccept);
                q.setFirstResult((p - 1) * ps);
            }

            String pageAccept = params.get("pageAccept");
            if (pageAccept != null && !pageAccept.isEmpty() && accept == true) {
                int p = Integer.parseInt(pageAccept);
                q.setFirstResult((p - 1) * ps);
            }
        }
        q.setMaxResults(ps);

        return q.getResultList();
    }

    @Override
    public boolean updateIsActiveGroupTransaction(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaUpdate<GroupTransaction> updateQuery = b.createCriteriaUpdate(GroupTransaction.class);
        Root<GroupTransaction> root = updateQuery.from(GroupTransaction.class);

        Predicate idPredicate = b.equal(root.get("id"), id);

        updateQuery.set("isActive", true);
        updateQuery.where(idPredicate);

        Query q = session.createQuery(updateQuery);
        int updatedCount = q.executeUpdate();
        return updatedCount > 0;
    }

    @Override
    public Long countGroupTransactionByGroupId(int groupId, boolean accept) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<GroupTransaction> root = query.from(GroupTransaction.class);

        Predicate groupIdPredicate = builder.equal(root.get("groupId"), groupId);
        Predicate activePredicate = null;

        if (accept == true) {
            activePredicate = builder.equal(root.get("isActive"), 1);
        } else {
            activePredicate = builder.equal(root.get("isActive"), 0);
        }

        Predicate finalPredicate = builder.and(activePredicate, groupIdPredicate);

        query.select(builder.count(root)).where(finalPredicate);

        TypedQuery<Long> typedQuery = session.createQuery(query);
        return typedQuery.getSingleResult();
    }

    @Override
    public double getAmountGroupTransactionByGroupId(int groupId, int typeId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Double> query = builder.createQuery(Double.class);
        Root<GroupTransaction> root = query.from(GroupTransaction.class);

        Predicate groupIdPredicate = builder.equal(root.get("groupId"), groupId);
        Predicate typePredicate = null;
        Predicate activePredicate = builder.equal(root.get("isActive"), 1);

        if (typeId == 1) {
            typePredicate = builder.equal(root.get("typeId"), 1);
        } else if (typeId == 2) {
            typePredicate = builder.equal(root.get("typeId"), 2);
        }

        Predicate finalPredicate = builder.and(typePredicate, groupIdPredicate, activePredicate);

        query.select(builder.sum(root.get("amount").as(Double.class))).where(finalPredicate);

        TypedQuery<Double> typedQuery = session.createQuery(query);
        Double totalAmount = typedQuery.getSingleResult();

        return totalAmount != null ? totalAmount : 0.0;
    }

}
