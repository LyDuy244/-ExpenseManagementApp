/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.validator;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.GroupTransaction;
import com.lnnd.service.GroupExpenseService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author ADMIN
 */
@Component
public class GroupTransactionCreatedDateValidator implements Validator {

    private final GroupExpenseService groupExpenseService;

    public GroupTransactionCreatedDateValidator(GroupExpenseService groupExpenseService) {
        this.groupExpenseService = groupExpenseService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return GroupTransaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GroupTransaction grTran = (GroupTransaction) target;
        GroupExpense gr = groupExpenseService.getGroupExpenseById(grTran.getGroupId().getId());

        if (grTran.getCreatedDate() != null) {
            if (grTran.getCreatedDate().compareTo(gr.getEndDate()) > 0) {
                errors.rejectValue("createdDate", "groupTransaction.createdDate.inValid");
            }
        }

    }
}
