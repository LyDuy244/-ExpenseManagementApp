/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.Transaction;
import com.lnnd.service.TransactionService;
import com.lnnd.service.impl.MyUserDetails;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class TransactionController {

    @Autowired
    private TransactionService tranSer;

    int pageSize = 10;

    @GetMapping("/transactions")
    public String list(Model model, @RequestParam Map<String, String> params,
            @RequestParam(name = "ps", defaultValue = "10") String ps,
            @AuthenticationPrincipal MyUserDetails user) {

//        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        String size = params.get("ps");
        if (size != null) {
            pageSize = Integer.parseInt(size);
        }
        Long count = this.tranSer.countTransaction();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        model.addAttribute("pagesize", size);
        model.addAttribute("transactions", this.tranSer.getAllTransactionsByUserId(user.getId(),params, pageSize));
        return "transactions";
    }

    @GetMapping("/transactions/add")
    public String addPage(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "addTransaction";
    }

    @PostMapping("/transactions/add")
    public String add(@ModelAttribute(value = "transaction") Transaction t) {
        if (tranSer.addOrUpdateTransaction(t) == true) {
            return "redirect:/transactions";
        }
        return "addTransaction";
    }
    
    @GetMapping("/transactions/{id}")
    public String update(Model model, @PathVariable(value = "id") int id)  {
        model.addAttribute("transaction", this.tranSer.getTransactionById(id));
        return "addTransaction";
    }
}
