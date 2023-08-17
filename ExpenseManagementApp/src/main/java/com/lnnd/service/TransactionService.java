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
    List<Transaction> getTransactions(Map<String, String> params, int pageSize);
    Long countTransaction();
    boolean addOrUpdateTransaction(Transaction t);
    Transaction getTransactionById(int id);
    List<Transaction> getAllTransactionsByUserId(int userId, Map<String, String> params, int pageSize);
}
