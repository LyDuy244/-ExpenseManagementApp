/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service.impl;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.repository.GroupExpenseRepository;
import com.lnnd.service.GroupExpenseService;
import java.util.Date;
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

}
