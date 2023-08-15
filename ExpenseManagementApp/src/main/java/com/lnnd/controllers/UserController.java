/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.User;
import com.lnnd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ADMIN
 */
@Controller
public class UserController {
    @Autowired  
    private UserService userDetailsService; 
    
    
    @GetMapping("/login")
    public String addPage(Model model) {
        return "login";
    }
    
    @GetMapping("/register")
    public String registerView(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute(value = "user") User u){
        String errorMsg = "";
        if(u.getPassword().equals(u.getConfirmPassword())){
            if(this.userDetailsService.addUser(u) == true)
                return "redirect:/login";
            else
                errorMsg = "Đã có lỗi xảy ra";
        }else
            errorMsg = "Mật khẩu không khớp";
        model.addAttribute("errorMsg", errorMsg);
        return "register";
    }
}
