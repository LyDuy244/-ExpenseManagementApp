/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lnnd.service;

import com.lnnd.pojo.GroupTransaction;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface GroupTransactionService {

    boolean addGroupTransaction(GroupTransaction grTransaction);

    List<GroupTransaction> getGroupTransactionByGroupId(int groupId, boolean accept, Map<String, String> params);

    boolean updateIsActiveGroupTransaction(int id);

    Long countGroupTransactionByGroupId(int groupId, boolean accept);

    double getAmountGroupTransactionByGroupId(int groupId, int typeId);
}
