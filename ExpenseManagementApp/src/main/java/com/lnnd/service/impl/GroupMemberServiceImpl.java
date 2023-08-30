/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service.impl;

import com.lnnd.pojo.GroupMember;
import com.lnnd.repository.GroupMemberRepository;
import com.lnnd.service.GroupExpenseService;
import com.lnnd.service.GroupMemberService;
import com.lnnd.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepo;
    
    @Autowired
    private GroupExpenseService groupExpenseService;
    
    @Autowired
    private UserService userService;

    @Override
    public boolean addGroupMember(GroupMember gm) {
        gm.setIsActive(true);
        return this.groupMemberRepo.addGroupMember(gm);
    }

    @Override
    public GroupMember getGroupMemberByUserIdAndGroupId(int userId, int groupId) {
        return this.groupMemberRepo.getGroupMemberByUserIdAndGroupId(userId, groupId);
    }

    @Override
    public boolean acceptGroup(int userId, int groupId) {
        GroupMember grMember = groupMemberRepo.getGroupMemberByUserIdAndGroupId(userId, groupId);
        if (grMember == null) {
            GroupMember member = new GroupMember();
            member.setUserId(userService.getUserById(userId));
            member.setGroupId(groupExpenseService.getGroupExpenseById(groupId));
            member.setIsActive(true);
            groupMemberRepo.addGroupMember(member);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Object[]> getGroupMembersExpensesByGroupId(int groupId) {
        return this.groupMemberRepo.getGroupMembersExpensesByGroupId(groupId);
    }
}
