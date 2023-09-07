/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.Notification;
import com.lnnd.repository.NotificationRepository;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean addNotification(Notification noti) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.save(noti);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Notification getNotificationByInfo(int userId, int month, int quarter, int year, boolean type) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Notification> query = b.createQuery(Notification.class);
        Root<Notification> root = query.from(Notification.class);

        Predicate userIdPredicate = b.equal(root.get("userId"), userId);
        Predicate monthNotiPredicate = b.equal(root.get("monthNoti"), month);
        Predicate quarterNotiPredicate = b.equal(root.get("quarter"), quarter);
        Predicate yearNotiPredicate = b.equal(root.get("yearNoti"), year);
        Predicate typeNotiPredicate = b.equal(root.get("type"), type);

        Predicate finalPredicate = b.and(userIdPredicate, monthNotiPredicate, quarterNotiPredicate, yearNotiPredicate,typeNotiPredicate);

        query.select(root).where(finalPredicate);

        Query q = session.createQuery(query);
        List<Notification> notiList = q.getResultList();

        if (!notiList.isEmpty()) {
            return notiList.get(0);
        } else {
            return null; // Trả về null khi không có đối tượng
        }
    }

}
