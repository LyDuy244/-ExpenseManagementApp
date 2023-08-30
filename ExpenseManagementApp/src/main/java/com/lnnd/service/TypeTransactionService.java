/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lnnd.service;

import com.lnnd.pojo.TypeTransaction;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface TypeTransactionService {
    List<TypeTransaction> getTypeTransaction();
    TypeTransaction getTypeTransactionById(int id);
}
