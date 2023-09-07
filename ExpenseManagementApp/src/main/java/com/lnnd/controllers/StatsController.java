/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.service.GroupTransactionService;
import com.lnnd.service.TransactionService;
import com.lnnd.service.UserService;
import com.lnnd.service.impl.MyUserDetails;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
public class StatsController {

    @Autowired
    private TransactionService tranSer;

    @Autowired
    private UserService userSer;
    
    @Autowired
    private GroupTransactionService grTransactionSer;

    @GetMapping("/stats/transaction")
    public String statsTransactionView(Model model, @RequestParam Map<String, String> params,
            @AuthenticationPrincipal MyUserDetails user) {

        int currentYear = LocalDate.now().getYear();

        model.addAttribute("stats", tranSer.getTransactionStatisticsByUserId(user.getId(), params));
        model.addAttribute("years", tranSer.getTransactionYearsByUserId(user.getId()));
        model.addAttribute("currentYear", currentYear);

        return "stats_transaction";
    }

    @GetMapping("/stats/group-transaction")
    public String statsGroupTransactionView(Model model, @RequestParam Map<String, String> params,
            @AuthenticationPrincipal MyUserDetails user) {

        int currentYear = LocalDate.now().getYear();

        model.addAttribute("stats", this.grTransactionSer.getGroupTransactionStatisticsByUserId(user.getId(), params));
        model.addAttribute("years", this.grTransactionSer.getGroupTransactionYearsByUserId(user.getId()));
        model.addAttribute("currentYear", currentYear);

        return "stats_group_transaction";
    }
}
