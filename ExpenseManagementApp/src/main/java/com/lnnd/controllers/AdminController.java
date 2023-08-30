/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.User;
import com.lnnd.service.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource("classpath:configs.properties")
public class AdminController {

    @Autowired
    private UserService userSer;

    @Autowired
    private Environment env;

    @GetMapping("/manage-user")
    public String manageUser(Model model, @RequestParam Map<String, String> params) {

        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        
        Long count = this.userSer.countUser();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        model.addAttribute("u", new User());
        model.addAttribute("users", this.userSer.getAllUserPagination(params));
        model.addAttribute("count", count);
        return "manageUser";
    }

    @PostMapping("/manage-user")
    public String changeActiveUser(Model model, @ModelAttribute(value = "u") User user) {

        User u = this.userSer.getUserById(user.getId());
        boolean isActive = u.getIsActive();

        if (isActive == false) {
            this.userSer.isActive(u.getId());
        } else {
            this.userSer.isBlock(u.getId());
        }

        return "redirect:/manage-user";
    }
}
