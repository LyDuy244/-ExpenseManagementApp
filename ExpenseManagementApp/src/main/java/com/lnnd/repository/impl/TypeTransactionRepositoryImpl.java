/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository.impl;

import com.lnnd.pojo.TypeTransaction;
import com.lnnd.repository.TypeTransactionRepository;
import java.util.List;
import javax.persistence.Query;
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
public class TypeTransactionRepositoryImpl implements TypeTransactionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<TypeTransaction> getTypeTransaction() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM TypeTransaction");

        return q.getResultList();
    }

    @Override
    public TypeTransaction getTypeTransactionById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(TypeTransaction.class, id);
    }

}
