/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lnnd.service;

import com.lnnd.pojo.GroupMember;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface GroupMemberService {

    boolean addGroupMember(GroupMember gm);

    GroupMember getGroupMemberByUserIdAndGroupId(int userId, int groupId);

    boolean acceptGroup(int userId, int groupId);

    List<Object[]> getGroupMembersExpensesByGroupId(int groupId);
}
