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
    List<Transaction> getTransactions(Map<String, String> params, int pageSize);
    Long countTransaction();
    boolean addOrUpdateTransaction(Transaction t);
}
