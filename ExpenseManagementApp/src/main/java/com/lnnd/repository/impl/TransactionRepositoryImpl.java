/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.Transaction;
import com.lnnd.repository.TransactionRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private Environment env;

    @Autowired
    private SimpleDateFormat f;

    @Override
    public Long countTransaction(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Transaction> root = query.from(Transaction.class);

        query.select(builder.count(root))
                .where(builder.equal(root.get("userId"), userId));

        TypedQuery<Long> typedQuery = session.createQuery(query);
        return typedQuery.getSingleResult();
    }

    @Override
    public boolean addOrUpdateTransaction(Transaction t) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (t.getId() == null) {
                s.save(t);
            } else {
                s.update(t);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Transaction getTransactionById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Transaction.class, id);
    }

    static int ps = 10;

    @Override
    public List<Transaction> getAllTransactionsByUserId(int userId, Map<String, String> params, int pageSize) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Transaction> q = b.createQuery(Transaction.class);
        Root root = q.from(Transaction.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("purpose"), String.format("%%%s%%", kw)));
            }

            String typeId = params.get("typeId");
            if (typeId != null && !typeId.isEmpty()) {
                predicates.add(b.equal(root.get("typeId"), Integer.parseInt(typeId)));
            }

            String fd = params.get("fromDate");
            if (fd != null && !fd.isEmpty()) {
                try {
                    predicates.add(b.greaterThanOrEqualTo(root.get("createdDate"), f.parse(fd)));
                } catch (ParseException ex) {
                    Logger.getLogger(TransactionRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String td = params.get("toDate");
            if (td != null && !td.isEmpty()) {
                try {
                    predicates.add(b.lessThanOrEqualTo(root.get("createdDate"), f.parse(td)));
                } catch (ParseException ex) {
                    Logger.getLogger(TransactionRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            predicates.add(b.equal(root.get("isActive"), 1));
            predicates.add(b.equal(root.get("userId"), userId));

            Predicate[] predicateArray = new Predicate[predicates.size()];
            for (int i = 0; i < predicates.size(); i++) {
                predicateArray[i] = predicates.get(i);
            }

            q.where(predicateArray);
        }

        q.orderBy(b.asc(root.get("id")));
        Query query = session.createQuery(q);

        if (ps != pageSize) {
            ps = pageSize;
            query.setMaxResults(ps);
        }
        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
//                int ps = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
                int p = Integer.parseInt(page);
                query.setMaxResults(ps);
                query.setFirstResult((p - 1) * ps);
            }

        }
        return query.getResultList();
    }

    @Override
    public double getTotalAmountMonthOfTransactionsByUserId(int userId, String timePeriod) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Double> query = builder.createQuery(Double.class);
        Root<Transaction> root = query.from(Transaction.class);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth;
        LocalDateTime endOfMonth;

        if (timePeriod.equals("present")) {
            startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            endOfMonth = now.withDayOfMonth(now.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        } else if (timePeriod.equals("past")) {
            // Lấy thông tin tháng trước
            LocalDateTime lastMonth = now.minusMonths(1);
            startOfMonth = lastMonth.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            endOfMonth = lastMonth.withDayOfMonth(lastMonth.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        } else {
            throw new IllegalArgumentException("Invalid time period");
        }

        query.select(builder.sum(root.get("amount").as(Double.class)))
                .where(
                        builder.and(
                                builder.equal(root.get("userId"), userId),
                                builder.equal(root.get("typeId"), 2),
                                builder.between(
                                        root.get("createdDate").as(java.util.Date.class),
                                        Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant()),
                                        Date.from(endOfMonth.atZone(ZoneId.systemDefault()).toInstant())
                                )
                        )
                );

        TypedQuery<Double> typedQuery = session.createQuery(query);
        Double totalAmount = typedQuery.getSingleResult();

        return totalAmount != null ? totalAmount : 0.0;
    }

    @Override
    public double getTotalAmountQuarterOfTransactionsByUserId(int userId, String timePeriod) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Double> query = builder.createQuery(Double.class);
        Root<Transaction> root = query.from(Transaction.class);

        LocalDateTime now = LocalDateTime.now();
        int currentQuarter = (now.getMonthValue() - 1) / 3 + 1; // Xác định quý hiện tại

        LocalDateTime startOfQuarter;
        LocalDateTime endOfQuarter;

        if (timePeriod.equals("present")) {
            startOfQuarter = now.withMonth((currentQuarter - 1) * 3 + 1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            endOfQuarter = startOfQuarter.plusMonths(3).minusNanos(1);
        } else if (timePeriod.equals("past")) {
            int lastQuarter = currentQuarter - 1;
            if (lastQuarter < 1) {
                lastQuarter = 4;
            }
            startOfQuarter = now.minusMonths(3).withMonth((lastQuarter - 1) * 3 + 1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            endOfQuarter = startOfQuarter.plusMonths(3).minusNanos(1);
        } else {
            throw new IllegalArgumentException("Invalid time period");
        }

        query.select(builder.sum(root.get("amount").as(Double.class)))
                .where(
                        builder.and(
                                builder.equal(root.get("userId"), userId),
                                builder.equal(root.get("typeId"), 2), // Assuming typeId 1 represents expenses
                                builder.between(
                                        root.get("createdDate").as(java.util.Date.class),
                                        Date.from(startOfQuarter.atZone(ZoneId.systemDefault()).toInstant()),
                                        Date.from(endOfQuarter.atZone(ZoneId.systemDefault()).toInstant())
                                )
                        )
                );

        TypedQuery<Double> typedQuery = session.createQuery(query);
        Double totalExpense = typedQuery.getSingleResult();

        return totalExpense != null ? totalExpense : 0.0;
    }

}
