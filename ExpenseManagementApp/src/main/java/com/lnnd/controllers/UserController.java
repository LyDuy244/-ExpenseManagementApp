/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.configs.Utility;
import com.lnnd.pojo.User;
import com.lnnd.service.UserService;
import com.lnnd.service.impl.MyUserDetails;
import com.lnnd.validator.RegisterEmailValidator;
import com.lnnd.validator.RegisterValidator;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
public class UserController {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private RegisterValidator registerValidator;

    @InitBinder
    public void initBinder(WebDataBinder bider) {
        bider.setValidator(registerValidator);
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute(value = "user") @Valid User u,
            BindingResult rs, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException  {
        String errorMsg = null;

        if (!rs.hasErrors()) {

            if (this.userDetailsService.addUser(u) == true) {
                String siteUrl = Utility.getSiteUrl(request);
                this.userDetailsService.sendVerificationEmail(u, siteUrl);
                return "redirect:/register_success";
            } else {
                errorMsg = "Đã có lỗi xảy ra";
            }

        } else {
            System.out.println(rs);
        }

        model.addAttribute("errorMsg", errorMsg);
        return "register";
    }

    @GetMapping("/register_success")
    public String register_success(Model model) {
        return "register_success";
    }

    @GetMapping("/verify_success")
    public String verify_success() {
        return "verify_success";
    }

    @GetMapping("/verify_fail")
    public String verify_fail() {
        return "verify_fail";
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("code") String code, Model model) {
        boolean verified = userDetailsService.verify(code);

        String pageTitle = verified ? "Verification Succeeded!" : "Vertification Failed";
        model.addAttribute("pageTitle", pageTitle);

        return verified ? "verify_success" : "verify_fail";
    }

    @GetMapping("/user-details")
    public String userDetails(Model model,
            @AuthenticationPrincipal MyUserDetails user) {
        model.addAttribute("user", this.userDetailsService.getUserById(user.getId()));
        return "userDetails";
    }

    @PostMapping("/user-details")
    public String updateUser(Model model, @ModelAttribute(value = "user") User u,
            @AuthenticationPrincipal MyUserDetails user) {
        if (this.userDetailsService.updateUser(user.getId(), u) == true) {
            user.setFirstName(u.getFirstName());
            user.setLastName(u.getLastName());
            return "redirect:/transactions";
        }

        return "index";
    }
}
