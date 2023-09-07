/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service.impl;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.repository.GroupExpenseRepository;
import com.lnnd.service.GroupExpenseService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class GroupExpenseServiceImpl implements GroupExpenseService {

    @Autowired
    private GroupExpenseRepository groupRepo;

    @Override
    public boolean addGroup(GroupExpense gr) {
        gr.setIsActive(true);
        return this.groupRepo.addGroup(gr);
    }

    @Override
    public List<GroupExpense> getGroupExpensesByUserId(int userId,  Map<String, String> params) {
        return this.groupRepo.getGroupExpensesByUserId(userId, params);
    }

    @Override
    public GroupExpense getGroupExpenseById(int id) {
        return this.groupRepo.getGroupExpenseById(id);
    }

    @Override
    public GroupExpense getGroupExpenseByInfo(GroupExpense gr) {
        return this.groupRepo.getGroupExpenseByInfo(gr);
    }

    @Override
    public Long countGroupExpenseByUserId(int userId) {
        return this.groupRepo.countGroupExpenseByUserId(userId);
    }

}
