/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service.impl;

import com.lnnd.pojo.TypeTransaction;
import com.lnnd.repository.TypeTransactionRepository;
import com.lnnd.service.TypeTransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class TypeTransactionServiceImpl implements TypeTransactionService{
    @Autowired
    private TypeTransactionRepository typeTransactionRepo;
    
    
    @Override
    public List<TypeTransaction> getTypeTransaction() {
        return this.typeTransactionRepo.getTypeTransaction();
    }
    
}
