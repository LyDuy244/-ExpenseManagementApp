/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.repository.GroupExpenseRepository;
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
public class GroupExpenseRepositoryImpl implements GroupExpenseRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

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

}
