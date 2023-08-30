/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.Transaction;
import com.lnnd.pojo.User;
import com.lnnd.service.TransactionService;
import com.lnnd.service.TypeTransactionService;
import com.lnnd.service.UserService;
import com.lnnd.service.impl.MyUserDetails;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TransactionController {

    @Autowired
    private TransactionService tranSer;

    @Autowired
    private UserService userSer;

    @Autowired
    private TypeTransactionService typeTranSer;

    static int pageSize = 10;

    @GetMapping("/transactions")
    public String list(Model model, @RequestParam Map<String, String> params,
            @AuthenticationPrincipal MyUserDetails user) {

//        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        if (params.get("ps") != null) {
            pageSize = Integer.parseInt(params.get("ps"));
        }
        Long count = this.tranSer.countTransaction(user.getId());
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        model.addAttribute("type", this.typeTranSer.getTypeTransaction());
        model.addAttribute("transactions", this.tranSer.getAllTransactionsByUserId(user.getId(), params, pageSize));
        model.addAttribute("ps", pageSize);

        return "transactions";
    }

    @GetMapping("/transactions/add")
    public String addTransactionPage(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "addTransaction";
    }

    @PostMapping("/transactions/add")
    public String addTransaction(@ModelAttribute(value = "transaction") @Valid Transaction t,
            BindingResult rs,
            @AuthenticationPrincipal MyUserDetails user
    ) throws MessagingException, UnsupportedEncodingException {
        if (!rs.hasErrors()) {
            if (tranSer.addOrUpdateTransaction(t) == true) {
                User u = userSer.getUserById(user.getId());
                double amountThisQuarter = this.tranSer.getTotalAmountQuarterOfTransactionsByUserId(user.getId(), "present");
                double amountLastQuarter = this.tranSer.getTotalAmountQuarterOfTransactionsByUserId(user.getId(), "past");
                if (amountThisQuarter - amountLastQuarter > 5000000 && amountLastQuarter > 0) {
                    this.userSer.sendMailWarning(u, "quý này của quý khách đã vượt quá chi tiêu tháng trước (chi tiêu cá nhâ): " + (amountThisQuarter - amountLastQuarter) + " đồng. Xin hãy thu chi cẩn thận hơn");
                } else {
                    double amountThisMonth = this.tranSer.getTotalAmountMonthOfTransactionsByUserId(user.getId(), "present");
                    double amountLastMonth = this.tranSer.getTotalAmountMonthOfTransactionsByUserId(user.getId(), "past");
                    if (amountThisMonth > amountLastMonth && amountLastMonth > 0) {
                        this.userSer.sendMailWarning(u, "tháng");
                    }
                }

                return "redirect:/transactions";

            }
        } else {
            System.out.println(rs);
        }
        return "addTransaction";
    }

    @GetMapping("/transactions/{id}")
    public String update(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("transaction", this.tranSer.getTransactionById(id));
        return "addTransaction";
    }
}
