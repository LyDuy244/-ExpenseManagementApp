/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.formatters;

import com.lnnd.pojo.GroupExpense;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ADMIN
 */
public class GroupExpenseFormatter implements Formatter<GroupExpense> {

    @Override
    public String print(GroupExpense group, Locale locale) {
        return String.valueOf(group.getId());
    }

    @Override
    public GroupExpense parse(String groupId, Locale locale) throws ParseException {
        int id = Integer.parseInt(groupId);
        return new GroupExpense(id);
    }

}
