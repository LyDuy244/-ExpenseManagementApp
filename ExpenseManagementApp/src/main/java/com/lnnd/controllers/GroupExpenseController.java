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
import com.lnnd.service.UserService;
import com.lnnd.service.impl.MyUserDetails;
import com.lnnd.validator.GroupExpenseDateValidator;
import com.lnnd.validator.GroupExpenseValidator;
import com.lnnd.validator.RegisterValidator;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
@PropertySource("classpath:configs.properties")
public class GroupExpenseController {

    @Autowired
    private Environment env;

    @Autowired
    private GroupExpenseService groupSer;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupMemberService groupMemberSer;

    @Autowired
    private GroupExpenseValidator groupExpenseValidator;

    @InitBinder
    public void initBinder(WebDataBinder bider) {
        bider.setValidator(groupExpenseValidator);
    }

    @GetMapping("/group")
    public String list(Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam Map<String, String> params) {

        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

        Long count = this.groupSer.countGroupExpenseByUserId(user.getId());
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        model.addAttribute("groups", this.groupSer.getGroupExpensesByUserId(user.getId(), params));
        return "group";
    }

    @GetMapping("/group/add")
    public String addGroupPage(Model model) {
        model.addAttribute("group", new GroupExpense());
        return "addGroup";
    }

    @PostMapping("/group/add")
    public String addGroup(@ModelAttribute(value = "group") @Valid GroupExpense gr,
            BindingResult rs,
            @AuthenticationPrincipal MyUserDetails user) {

        if (!rs.hasErrors()) {
            if (groupSer.addGroup(gr) == true) {
                GroupExpense group = groupSer.getGroupExpenseByInfo(gr);
                User u = userService.getUserById(user.getId());
                GroupMember grMember = new GroupMember();
                grMember.setGroupId(group);
                grMember.setUserId(u);
                groupMemberSer.addGroupMember(grMember);

                return "redirect:/group";
            }
        }

        return "addGroup";
    }
}
