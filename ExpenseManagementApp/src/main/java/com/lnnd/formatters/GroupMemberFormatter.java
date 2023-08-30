/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.formatters;

import com.lnnd.pojo.GroupMember;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ADMIN
 */
public class GroupMemberFormatter implements Formatter<GroupMember>{

    @Override
    public String print(GroupMember groupMember, Locale locale) {
        return String.valueOf(groupMember.getId());
    }

    @Override
    public GroupMember parse(String groupMemberId, Locale locale) throws ParseException {
         int id = Integer.parseInt(groupMemberId);
        return new GroupMember(id);
    }
    
}
