/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.validator;

import com.lnnd.pojo.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author ADMIN
 */
@Component
public class TransactionAmountValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Transaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Transaction tran = (Transaction) target;
        
        if (tran.getAmount() < 1000) {
            errors.rejectValue("amount", "transaction.amount.inValid");
        }
    }

}
