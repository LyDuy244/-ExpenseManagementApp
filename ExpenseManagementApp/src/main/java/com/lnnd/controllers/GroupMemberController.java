/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.configs.Utility;
import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.GroupMember;
import com.lnnd.pojo.User;
import com.lnnd.service.GroupExpenseService;
import com.lnnd.service.GroupMemberService;
import com.lnnd.service.GroupTransactionService;
import com.lnnd.service.UserService;
import com.lnnd.service.impl.MyUserDetails;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
public class GroupMemberController {

    @Autowired
    private GroupExpenseService groupSer;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupMemberService groupMemberSer;

    @Autowired
    private GroupTransactionService groupTranSer;
    
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/group/details/{id}")
    public String detailsGroupPage(Model model,
            @PathVariable(value = "id") int id,
            @AuthenticationPrincipal MyUserDetails user) {
        Date now = new Date();

        double totalChi = this.groupTranSer.getAmountGroupTransactionByGroupId(id, 2);
        List<Object[]> memberList = this.groupMemberSer.getGroupMembersExpensesByGroupId(id);

      
        
        double avgChi = totalChi / memberList.size();

        model.addAttribute("totalChi", totalChi);
        model.addAttribute("avgChi", avgChi);

        model.addAttribute("users", this.userService.getAllUserWithoutCurrentUser(user.getId()));
        model.addAttribute("gr", this.groupSer.getGroupExpenseById(id));
        model.addAttribute("memberGroup", new GroupMember());
        model.addAttribute("groupMembers", memberList);
        model.addAttribute("currentDate", now);
        return "groupDetails";
    }

    @PostMapping("/group/details/{id}")
    public String addMemberGroup(Model model, @ModelAttribute(value = "memberGroup") GroupMember gm,
            @AuthenticationPrincipal MyUserDetails user,
            @PathVariable(value = "id") int id,
            BindingResult rs, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        GroupMember grMember = groupMemberSer.getGroupMemberByUserIdAndGroupId(gm.getUserId().getId(), gm.getGroupId().getId());

        if (grMember != null) {
            return "invite_fail";
        } else {
            String siteUrl = Utility.getSiteUrl(request);
            User userFrom = userService.getUserById(user.getId());
            User userTo = userService.getUserById(gm.getUserId().getId());
            GroupExpense group = groupSer.getGroupExpenseById(gm.getGroupId().getId());
            this.userService.sendInviteEmail(userFrom, userTo, group, siteUrl);
            return "invite_success";
        }

    }

    @GetMapping("/invite")
    public String verifyAccount(@RequestParam("userId") String userId, 
            @RequestParam("groupId") String groupId, Model model,
            Locale locale) {
        int user_id = Integer.parseInt(userId);
        int group_id = Integer.parseInt(groupId);
        String msg = null;

        List<Object[]> memberList = groupMemberSer.getGroupMembersExpensesByGroupId(group_id);

        boolean accept = false;
        if (memberList.size() < 4) {
            accept = groupMemberSer.acceptGroup(user_id, group_id);
            if (accept == false) {
                msg = messageSource.getMessage("invite.err.inGroup", null, locale);
            }
        } else {
            msg = messageSource.getMessage("invite.err.fullGroup", null, locale);
        }

        GroupExpense gr = groupSer.getGroupExpenseById(group_id);

        model.addAttribute("gr", gr);
        model.addAttribute("msg", msg);
        return accept ? "accept_success" : "accept_fail";
    }

    @GetMapping("/accept_success")
    public String accept_success() {
        return "accept_success";
    }

    @GetMapping("/accept_fail")
    public String accept_fail() {
        return "accept_fail";
    }

    @GetMapping("/invite_fail")
    public String invite_fail() {
        return "invite_fail";
    }

    @GetMapping("/invite_success")
    public String invite_success() {
        return "invite_success";
    }

}
