/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.User;
import com.lnnd.service.GroupExpenseService;
import com.lnnd.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
public class GroupExpenseController {

    @Autowired
    private GroupExpenseService groupSer;

    @Autowired
    private UserService userService;

    @GetMapping("/group")
    public String list(Model model) {
        return "group";
    }

    @GetMapping("/group/details")
    public String detailsGroupPage(Model model,
            @RequestParam(name = "username", defaultValue = "") String username) {
        List<User> users = this.userService.getUsers(username);
        User user = null;
        if (users.size() > 0) {
            user = users.get(0);
        }
        model.addAttribute("user", user);
        return "groupDetails";
    }

    @GetMapping("/group/add")
    public String addGroupPage(Model model) {
        model.addAttribute("group", new GroupExpense());
        return "addGroup";
    }

    @PostMapping("/group/add")
    public String addGroup(@ModelAttribute(value = "group") GroupExpense gr) {
        if (groupSer.addGroup(gr) == true) {
            return "redirect:/group";
        }
        return "addGroup";
    }
}
