/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.User;
import com.lnnd.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
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
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Override
    public boolean addUser(User user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(user);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public List<User> getUsers(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> query = b.createQuery(User.class);
        Root root = query.from(User.class);
        query = query.select(root);

        if (!username.isEmpty()) {
            Predicate p = b.equal(root.get("username").as(String.class), username.trim());
            Predicate isActivePredicate = b.equal(root.get("isActive"), 1);
            Predicate finalPredicate = b.and(p, isActivePredicate);

            query = query.where(finalPredicate);
        }

        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public User getUserById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public boolean updateUser(int id, User user) {
        Session session = this.factory.getObject().getCurrentSession();

        try {
            session.update(user);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public List<User> getAllUser() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User");

        return q.getResultList();
    }

    @Override
    public List<User> getAllUserWithoutCurrentUser(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> query = b.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);

        Predicate idNotEqualPredicate = b.notEqual(root.get("id"), userId);
        Predicate isActivePredicate = b.equal(root.get("isActive"), 1);

        List<String> excludedRoles = Arrays.asList("ROLE_BUSINESS", "ROLE_ADMIN", "ROLE_REPRESENTATIVE");
        Predicate rolePredicate = b.or(
                b.notEqual(root.get("userRole").as(String.class), excludedRoles.get(0)),
                b.notEqual(root.get("userRole").as(String.class), excludedRoles.get(1)),
                b.notEqual(root.get("userRole").as(String.class), excludedRoles.get(2))
        );

        Predicate finalPredicate = b.and(idNotEqualPredicate, isActivePredicate, rolePredicate);

        query.where(finalPredicate);

        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public User getUserByVerificationCode(String verifyCode) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> query = b.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);

        Predicate verificationCode = b.equal(root.get("verificationCode"), verifyCode);

        query.where(verificationCode);

        Query q = session.createQuery(query);
        List<User> users = q.getResultList();
        if (!users.isEmpty()) {
            return users.get(0);

        } else {
            return null; // Trả về null khi không có đối tượng
        }
    }

    @Override
    public boolean isActive(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaUpdate<User> updateQuery = b.createCriteriaUpdate(User.class);
        Root<User> root = updateQuery.from(User.class);

        Predicate idPredicate = b.equal(root.get("id"), userId);

        updateQuery.set("isActive", true);
        updateQuery.where(idPredicate);

        Query q = session.createQuery(updateQuery);
        int updatedCount = q.executeUpdate();
        return updatedCount > 0;
    }

    @Override
    public boolean isBlock(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaUpdate<User> updateQuery = b.createCriteriaUpdate(User.class);
        Root<User> root = updateQuery.from(User.class);

        Predicate idPredicate = b.equal(root.get("id"), userId);

        updateQuery.set("isActive", false);
        updateQuery.where(idPredicate);

        Query q = session.createQuery(updateQuery);
        int updatedCount = q.executeUpdate();
        return updatedCount > 0;
    }

    @Override
    public List<User> getAllUserPagination(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root root = q.from(User.class);
        q.select(root);
        q.orderBy(b.asc(root.get("id")));

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("username"), String.format("%%%s%%", kw)));
            }

            String userRole = params.get("userRole");
            if (userRole != null && !userRole.isEmpty()) {
                predicates.add(b.like(root.get("userRole"), String.format("%%%s%%", userRole)));
            }

            Predicate[] predicateArray = new Predicate[predicates.size()];
            for (int i = 0; i < predicates.size(); i++) {
                predicateArray[i] = predicates.get(i);
            }

            q.where(predicateArray);
        }

        Query query = session.createQuery(q);
        int ps = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                query.setFirstResult((p - 1) * ps);
            }
        }
        query.setMaxResults(ps);

        return query.getResultList();
    }

    @Override
    public Long countUser() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        query.select(builder.count(root));

        TypedQuery<Long> typedQuery = session.createQuery(query);
        return typedQuery.getSingleResult();
    }
}
