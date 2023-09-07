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
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Long countTransaction(int user_id) {
        return this.transactionRepository.countTransaction(user_id);
    }

    @Override
    public boolean addOrUpdateTransaction(Transaction t) {
        t.setIsActive(true);
        return this.transactionRepository.addOrUpdateTransaction(t);
    }

    @Override
    public Transaction getTransactionById(int id) {
        return this.transactionRepository.getTransactionById(id);
    }

    @Override
    public List<Transaction> getAllTransactionsByUserId(int userId, Map<String, String> params, int pageSize) {
        return this.transactionRepository.getAllTransactionsByUserId(userId, params, pageSize);
    }

    @Override
    public long getTotalAmountMonthOfTransactionsByUserId(int userId, String timePeriod) {
        return this.transactionRepository.getTotalAmountMonthOfTransactionsByUserId(userId, timePeriod);
    }

    @Override
    public long getTotalAmountQuarterOfTransactionsByUserId(int userId, String timePeriod) {
        return this.transactionRepository.getTotalAmountQuarterOfTransactionsByUserId(userId, timePeriod);

    }

    @Override
    public List<Object[]> getTransactionStatisticsByUserId(int userId, Map<String, String> params) {
        return this.transactionRepository.getTransactionStatisticsByUserId(userId, params);
    }

    @Override
    public List<Integer> getTransactionYearsByUserId(int userId) {
        return this.transactionRepository.getTransactionYearsByUserId(userId);
    }

    @Override
    public boolean deleteTransaction(int id) {
        return this.transactionRepository.deleteTransaction(id);
    }
}
