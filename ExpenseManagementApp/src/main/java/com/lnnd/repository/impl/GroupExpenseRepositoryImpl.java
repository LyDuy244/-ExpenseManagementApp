/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.GroupMember;
import com.lnnd.pojo.GroupTransaction;
import com.lnnd.repository.GroupExpenseRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
public class GroupExpenseRepositoryImpl implements GroupExpenseRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Override
    public boolean addGroup(GroupExpense gr) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(gr);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public List<GroupExpense> getGroupExpensesByUserId(int userId, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<GroupExpense> query = b.createQuery(GroupExpense.class);

        Root<GroupExpense> root = query.from(GroupExpense.class);
        Root<GroupMember> groupMemberRoot = query.from(GroupMember.class);

        Predicate joinPredicate = b.and(
                b.equal(groupMemberRoot.get("groupId"), root),
                b.equal(groupMemberRoot.get("userId"), userId)
        );

        query.select(root).where(joinPredicate);

        Query q = session.createQuery(query);

        int ps = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                q.setFirstResult((p - 1) * ps);
            }
        }
        q.setMaxResults(ps);

        return q.getResultList();
    }

    @Override
    public GroupExpense getGroupExpenseById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(GroupExpense.class, id);
    }

    @Override
    public GroupExpense getGroupExpenseByInfo(GroupExpense gr) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<GroupExpense> query = b.createQuery(GroupExpense.class);
        Root<GroupExpense> root = query.from(GroupExpense.class);

        Predicate predicateName = b.equal(root.get("name"), gr.getName());
        Predicate predicateTitle = b.equal(root.get("title"), gr.getTitle());
        Predicate predicateDescription = b.equal(root.get("description"), gr.getDescription());
        Predicate predicateOwnerId = b.equal(root.get("ownerId"), gr.getOwnerId().getId());
        Predicate finalPredicate = b.and(predicateName, predicateTitle, predicateDescription, predicateOwnerId);

        query.select(root).where(finalPredicate);

        Query q = session.createQuery(query);
        List<GroupExpense> grList = q.getResultList();

        if (!grList.isEmpty()) {
            return grList.get(0);
        } else {
            return null; // Trả về null khi không có đối tượng
        }
    }

    @Override
    public Long countGroupExpenseByUserId(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<GroupExpense> root = query.from(GroupExpense.class);

        Root<GroupMember> groupMemberRoot = query.from(GroupMember.class);

        Predicate joinPredicate = builder.and(
                builder.equal(groupMemberRoot.get("groupId"), root),
                builder.equal(groupMemberRoot.get("userId"), userId)
        );
        

        query.select(builder.count(root)).where(joinPredicate);

        TypedQuery<Long> typedQuery = session.createQuery(query);
        return typedQuery.getSingleResult();
    }

}
