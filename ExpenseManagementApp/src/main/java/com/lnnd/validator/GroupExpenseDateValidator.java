/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.validator;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.service.GroupExpenseService;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author ADMIN
 */
@Component
public class GroupExpenseDateValidator implements Validator {

    private final GroupExpenseService groupExpenseService;

    public GroupExpenseDateValidator(GroupExpenseService groupExpenseService) {
        this.groupExpenseService = groupExpenseService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return GroupExpense.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GroupExpense group = (GroupExpense) target;

        Date currentDate = new Date(); // Thời điểm hiện tại
        long oneDayInMilliseconds = 24 * 60 * 60 * 1000; // Một ngày trong mili giây

        Date now = new Date(currentDate.getTime() - oneDayInMilliseconds);

        if (group.getStartDate() != null && group.getEndDate() == null) {
            if (now.compareTo(group.getStartDate()) > 0) {
                errors.rejectValue("startDate", "groupExpense.startDate.invalid");
            }
        }
        if (group.getEndDate() != null && group.getStartDate() == null) {
            if (now.compareTo(group.getEndDate()) > 0) {
                errors.rejectValue("endDate", "groupExpense.endDate.invalid");
            }
        }

        if (group.getStartDate() != null && group.getEndDate() != null) {
            if (now.compareTo(group.getStartDate()) > 0) {
                errors.rejectValue("startDate", "groupExpense.startDate.invalid");
            }
            if (now.compareTo(group.getEndDate()) > 0) {
                errors.rejectValue("endDate", "groupExpense.endDate.invalid");
            }

            if (now.compareTo(group.getStartDate()) < 0 && now.compareTo(group.getEndDate()) < 0) {
                if (group.getStartDate().compareTo(group.getEndDate()) > 0) {
                    errors.rejectValue("startDate", "groupExpense.startDate.greaterEndDate");
                } else {
                    long timeDifference = group.getEndDate().getTime() - group.getStartDate().getTime();
                    long daysBetween = TimeUnit.MILLISECONDS.toDays(timeDifference);
                    if (daysBetween < 3) {
                        errors.rejectValue("startDate", "groupExpense.startDate.greater3Days");
                    }
                }
            }

        }
    }
}
