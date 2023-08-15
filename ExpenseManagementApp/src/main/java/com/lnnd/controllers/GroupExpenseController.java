/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.GroupExpense;
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
public class GroupExpenseController {

    @GetMapping("/group")
    public String list(Model model) {
        return "group";
    }

    @GetMapping("/group/add")
    public String addPage(Model model) {
        model.addAttribute("transaction", new GroupExpense());
        return "addTransaction";
    }

    @PostMapping("/group/add")
    public String add(@ModelAttribute(value = "groupExpense") GroupExpense t) {
//        if (tranSer.addOrUpdateTransaction(t) == true) {
//            return "redirect:/transactions";
//        }
        return "addTransaction";
    }
}
