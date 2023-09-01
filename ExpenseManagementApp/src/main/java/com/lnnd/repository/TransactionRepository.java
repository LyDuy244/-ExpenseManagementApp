/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lnnd.repository;

import com.lnnd.pojo.Transaction;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface TransactionRepository {

    Long countTransaction(int id);

    boolean addOrUpdateTransaction(Transaction t);

    Transaction getTransactionById(int id);

    List<Transaction> getAllTransactionsByUserId(int userId, Map<String, String> params, int pageSize);

    double getTotalAmountMonthOfTransactionsByUserId(int userId, String timePeriod);

    double getTotalAmountQuarterOfTransactionsByUserId(int userId, String timePeriod);

    List<Object[]> getTransactionStatisticsByUserId(int userId, Map<String, String> params);

    List<Integer> getTransactionYearsByUserId(int userId);
}
