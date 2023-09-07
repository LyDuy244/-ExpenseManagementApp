/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.GroupMember;
import com.lnnd.pojo.GroupTransaction;
import com.lnnd.pojo.TypeTransaction;
import com.lnnd.repository.GroupTransactionRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
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
                q.setMaxResults(ps);
                q.setFirstResult((p - 1) * ps);
            }

            String pageAccept = params.get("pageAccept");
            if (pageAccept != null && !pageAccept.isEmpty() && accept == true) {
                int p = Integer.parseInt(pageAccept);
                q.setMaxResults(ps);
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

    @Override
    public List<GroupTransaction> getGroupTransactionByGroupIdAndUserId(int groupId, int userId, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<GroupTransaction> query = builder.createQuery(GroupTransaction.class);

        Root<GroupTransaction> groupTransactionRoot = query.from(GroupTransaction.class);
        Join<GroupTransaction, GroupMember> groupMemberJoin = groupTransactionRoot.join("groupMemberId");
        Join<GroupTransaction, GroupExpense> groupExpenseJoin = groupTransactionRoot.join("groupId");

        query.select(groupTransactionRoot);

        query.where(
                builder.and(
                        builder.equal(groupExpenseJoin.get("id"), groupId),
                        builder.equal(groupMemberJoin.get("userId"), userId),
                        builder.equal(groupTransactionRoot.get("isActive"), false)
                )
        );

        Query q = session.createQuery(query);

        int ps = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                q.setMaxResults(ps);
                q.setFirstResult((p - 1) * ps);
            }
        }
        q.setMaxResults(ps);

        return q.getResultList();
    }

    @Override
    public boolean deleteGroupTransaction(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        GroupTransaction grTran = this.getGroupTransactionById(id);

        try {
            session.delete(grTran);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public GroupTransaction getGroupTransactionById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(GroupTransaction.class, id);
    }

    @Override
    public boolean updateGroupTransaction(int i, GroupTransaction gt) {
        Session session = this.factory.getObject().getCurrentSession();

        try {
            session.update(gt);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public Long countGroupTransactionByGroupIdAndUserId(int groupId, int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<GroupTransaction> groupTransactionRoot = query.from(GroupTransaction.class);
        Join<GroupTransaction, GroupMember> groupMemberJoin = groupTransactionRoot.join("groupMemberId");

        query.where(
                builder.and(
                        builder.equal(groupTransactionRoot.get("groupId"), groupId),
                        builder.equal(groupTransactionRoot.get("isActive"), false),
                        builder.equal(groupMemberJoin.get("userId"), userId)
                )
        );

        query.select(builder.count(groupTransactionRoot));

        TypedQuery<Long> typedQuery = session.createQuery(query);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Object[]> getGroupTransactionStatisticsByUserId(int userId, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

        int fromDate = 1, toDate = 12, year = LocalDate.now().getYear();
        int tam = 0;

        if (params != null) {
            String fd = params.get("fromDate");
            if (fd != null && !fd.isEmpty()) {
                fromDate = Integer.parseInt(fd);
            }

            String td = params.get("toDate");
            if (td != null && !td.isEmpty()) {
                toDate = Integer.parseInt(td);
            }

            String y = params.get("year");
            if (y != null && !y.isEmpty()) {
                year = Integer.parseInt(y);
            }
        }

        if (fromDate > toDate) {
            tam = fromDate;
            fromDate = toDate;
            toDate = tam;
        }

        Root<GroupTransaction> groupTransactionRoot = query.from(GroupTransaction.class);
        Join<GroupTransaction, GroupMember> groupMemberJoin = groupTransactionRoot.join("groupMemberId");
        Join<GroupTransaction, TypeTransaction> typeTransactionJoin = groupTransactionRoot.join("typeId");

        Expression<Integer> month = builder.function("MONTH", Integer.class, groupTransactionRoot.get("createdDate"));
        Expression<Double> sumThu = builder.sum(
                builder.<Double>selectCase()
                        .when(builder.and(
                                builder.equal(typeTransactionJoin.get("id"), 1),
                                builder.between(month, fromDate, toDate)),
                                groupTransactionRoot.<Double>get("amount"))
                        .otherwise(0.0)
        );

        Expression<Double> sumChi = builder.sum(
                builder.<Double>selectCase()
                        .when(builder.and(
                                builder.equal(typeTransactionJoin.get("id"), 2),
                                builder.between(month, fromDate, toDate)),
                                groupTransactionRoot.<Double>get("amount"))
                        .otherwise(0.0)
        );

        query.multiselect(
                month.alias("Month"),
                sumThu.alias("Thu"),
                sumChi.alias("Chi")
        );

        query.where(
                builder.and(
                        builder.equal(groupMemberJoin.get("userId"), userId),
                        builder.equal(groupTransactionRoot.get("isActive"), true),
                        builder.between(month, fromDate, toDate),
                        builder.equal(builder.function("YEAR", Integer.class, groupTransactionRoot.get("createdDate")), year)
                )
        );

        query.groupBy(month);
        query.orderBy(builder.asc(month));
        TypedQuery<Object[]> typedQuery = session.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public List<Integer> getGroupTransactionYearsByUserId(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root<GroupTransaction> groupTransactionRoot = query.from(GroupTransaction.class);
        Join<GroupTransaction, GroupMember> groupMemberJoin = groupTransactionRoot.join("groupMemberId");

        query.select(builder.function("YEAR", Integer.class, groupTransactionRoot.get("createdDate")))
                .where(builder.and(
                        builder.equal(groupMemberJoin.get("userId"), userId),
                        builder.equal(groupTransactionRoot.get("isActive"), true)
                ))
                .groupBy(builder.function("YEAR", Integer.class, groupTransactionRoot.get("createdDate")));

        // Thực hiện query và lấy danh sách năm
        TypedQuery<Integer> typedQuery = session.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public long getTotalAmountMonthOfGroupTransactionsByUserId(int userId, String timePeriod) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class); // Đổi kiểu dữ liệu ở đây
        Root<GroupTransaction> groupTransactionRoot = query.from(GroupTransaction.class);
        Join<GroupTransaction, GroupMember> groupMemberJoin = groupTransactionRoot.join("groupMemberId");

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

        query.select(builder.sum(groupTransactionRoot.get("amount").as(Long.class))) // Đổi kiểu dữ liệu ở đây
                .where(
                        builder.and(
                                builder.equal(groupMemberJoin.get("userId"), userId),
                                builder.equal(groupTransactionRoot.get("typeId"), 2),
                                builder.equal(groupTransactionRoot.get("isActive"), true),
                                builder.between(
                                        groupTransactionRoot.get("createdDate").as(java.util.Date.class),
                                        Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant()),
                                        Date.from(endOfMonth.atZone(ZoneId.systemDefault()).toInstant())
                                )
                        )
                );

        TypedQuery<Long> typedQuery = session.createQuery(query); // Đổi kiểu dữ liệu ở đây
        Long totalAmount = typedQuery.getSingleResult(); // Đổi kiểu dữ liệu ở đây

        return totalAmount != null ? totalAmount : 0L; // Đổi 0.0 thành 0L
    }

    @Override
    public long getTotalAmountQuarterOfGroupTransactionsByUserId(int userId, String timePeriod) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class); // Đổi kiểu dữ liệu của query
        Root<GroupTransaction> groupTransactionRoot = query.from(GroupTransaction.class);
        Join<GroupTransaction, GroupMember> groupMemberJoin = groupTransactionRoot.join("groupMemberId");

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

        query.select(builder.sum(groupTransactionRoot.get("amount").as(Long.class))) // Đổi kiểu dữ liệu của select
                .where(
                        builder.and(
                                builder.equal(groupMemberJoin.get("userId"), userId),
                                builder.equal(groupTransactionRoot.get("typeId"), 2), // Assuming typeId 1 represents expenses
                                builder.between(
                                        groupTransactionRoot.get("createdDate").as(java.util.Date.class),
                                        Date.from(startOfQuarter.atZone(ZoneId.systemDefault()).toInstant()),
                                        Date.from(endOfQuarter.atZone(ZoneId.systemDefault()).toInstant())
                                )
                        )
                );

        TypedQuery<Long> typedQuery = session.createQuery(query); // Đổi kiểu dữ liệu của query
        Long totalExpense = typedQuery.getSingleResult(); // Đổi kiểu dữ liệu của biến

        return totalExpense != null ? totalExpense : 0L; // Đổi 0.0 thành 0L
    }

}
