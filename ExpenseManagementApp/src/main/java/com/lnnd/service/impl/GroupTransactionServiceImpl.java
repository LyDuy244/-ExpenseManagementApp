/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service.impl;

import com.lnnd.pojo.GroupMember;
import com.lnnd.pojo.GroupTransaction;
import com.lnnd.repository.GroupTransactionRepository;
import com.lnnd.service.GroupTransactionService;
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
public class GroupTransactionServiceImpl implements GroupTransactionService{

    @Autowired
    private GroupTransactionRepository grTransactionSer;
    
    @Override
    public boolean addGroupTransaction(GroupTransaction grTransaction) {
        grTransaction.setIsActive(false);
        grTransaction.setCreatedDate(new Date());
        return this.grTransactionSer.addGroupTransaction(grTransaction);
    }

   
    
    @Override
    public List<GroupTransaction> getGroupTransactionByGroupId(int groupId, boolean accept, Map<String, String> params) {
        return this.grTransactionSer.getGroupTransactionByGroupId(groupId,  accept, params);
    }

    @Override
    public boolean updateIsActiveGroupTransaction(int id) {
        return this.grTransactionSer.updateIsActiveGroupTransaction(id);
    }

    @Override
    public Long countGroupTransactionByGroupId(int groupId, boolean accept) {
        return this.grTransactionSer.countGroupTransactionByGroupId(groupId, accept);
    }

    @Override
    public double getAmountGroupTransactionByGroupId(int groupId, int typeId) {
        return this.grTransactionSer.getAmountGroupTransactionByGroupId(groupId, typeId);
    }

    @Override
    public List<GroupTransaction> getGroupTransactionByGroupIdAndUserId(int groupId, int userId, Map<String, String> params) {
        return this.grTransactionSer.getGroupTransactionByGroupIdAndUserId(groupId, userId, params);
    }

    @Override
    public GroupTransaction getGroupTransactionById(int id) {
        return this.grTransactionSer.getGroupTransactionById(id);
    }

    @Override
    public boolean deleteGroupTransaction(int id) {
        return this.grTransactionSer.deleteGroupTransaction(id);
    }

    @Override
    public boolean updateGroupTransaction(int id, GroupTransaction gt) {
        GroupTransaction groupTran = this.getGroupTransactionById(id);
        groupTran.setPurpose(gt.getPurpose());
        groupTran.setDescription(gt.getDescription());
        groupTran.setAmount(gt.getAmount());
        groupTran.setTypeId(gt.getTypeId());

       
        return this.grTransactionSer.updateGroupTransaction(id, groupTran);
    }

    @Override
    public Long countGroupTransactionByGroupIdAndUserId(int groupId, int userId) {
        return this.grTransactionSer.countGroupTransactionByGroupIdAndUserId(groupId, userId);
    }

    @Override
    public List<Object[]> getGroupTransactionStatisticsByUserId(int userId, Map<String, String> params) {
        return this.grTransactionSer.getGroupTransactionStatisticsByUserId(userId, params);
    }

    @Override
    public List<Integer> getGroupTransactionYearsByUserId(int userId) {
        return this.grTransactionSer.getGroupTransactionYearsByUserId(userId);
    }

    @Override
    public long getTotalAmountMonthOfGroupTransactionsByUserId(int userId, String timePeriod) {
        return this.grTransactionSer.getTotalAmountMonthOfGroupTransactionsByUserId(userId, timePeriod);
    }

    @Override
    public long getTotalAmountQuarterOfGroupTransactionsByUserId(int userId, String timePeriod) {
        return this.grTransactionSer.getTotalAmountQuarterOfGroupTransactionsByUserId(userId, timePeriod);
    }

   
    
}
