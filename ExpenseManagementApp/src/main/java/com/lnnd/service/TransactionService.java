/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lnnd.service;

import com.lnnd.pojo.Transaction;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface TransactionService {

    Long countTransaction(int user_id);

    boolean addOrUpdateTransaction(Transaction t);

    Transaction getTransactionById(int id);

    List<Transaction> getAllTransactionsByUserId(int userId, Map<String, String> params, int pageSize);

    long getTotalAmountMonthOfTransactionsByUserId(int userId, String timePeriod);

    long getTotalAmountQuarterOfTransactionsByUserId(int userId, String timePeriod);

    List<Object[]> getTransactionStatisticsByUserId(int userId, Map<String, String> params);

    List<Integer> getTransactionYearsByUserId(int userId);

    boolean deleteTransaction(int id);
    
}
