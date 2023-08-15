/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service.impl;

import com.lnnd.pojo.Transaction;
import com.lnnd.repository.TransactionRepository;
import com.lnnd.service.TransactionService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactions(Map<String, String> params, int pageSize) {
        return this.transactionRepository.getTransactions(params, pageSize);
    }

    @Override
    public Long countTransaction() {
        return this.transactionRepository.countTransaction();
    }

    @Override
    public boolean addOrUpdateTransaction(Transaction t) {
        t.setIsActive(true);
        t.setCreatedDate(new Date());
        return this.transactionRepository.addOrUpdateTransaction(t);
    }
}
