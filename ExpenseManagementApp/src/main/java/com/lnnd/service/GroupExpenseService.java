/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lnnd.service;

import com.lnnd.pojo.GroupExpense;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface GroupExpenseService {

    boolean addGroup(GroupExpense gr);

    List<GroupExpense> getGroupExpensesByUserId(int userId, Map<String, String> params);

    GroupExpense getGroupExpenseById(int id);

    GroupExpense getGroupExpenseByInfo(GroupExpense gr);

    Long countGroupExpenseByUserId(int userId);
}
