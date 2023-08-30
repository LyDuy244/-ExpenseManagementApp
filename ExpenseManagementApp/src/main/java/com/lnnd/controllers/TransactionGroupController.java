/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.GroupMember;
import com.lnnd.pojo.GroupTransaction;
import com.lnnd.service.GroupExpenseService;
import com.lnnd.service.GroupMemberService;
import com.lnnd.service.GroupTransactionService;
import com.lnnd.service.impl.MyUserDetails;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource("classpath:configs.properties")
public class TransactionGroupController {

    @Autowired
    private Environment env;

    @Autowired
    private GroupExpenseService groupSer;

    @Autowired
    private GroupTransactionService grTransactionSer;

    @Autowired
    private GroupMemberService grMemberSer;

    @GetMapping("/group/details/{id}/add-transaction")
    public String addGroupTransactionPage(Model model,
            @PathVariable(value = "id") int id,
            @AuthenticationPrincipal MyUserDetails user) {

        GroupMember grMember = grMemberSer.getGroupMemberByUserIdAndGroupId(user.getId(), id);
        Date now = new Date();

        model.addAttribute("gr", this.groupSer.getGroupExpenseById(id));
        model.addAttribute("groupTransaction", new GroupTransaction());
        model.addAttribute("memberGroup", grMember);
        model.addAttribute("currentDate", now);

        return "addTransactionGroup";
    }

    @PostMapping("/group/details/add-transaction")
    public String addGroupTransaction(Model model,
            @ModelAttribute(value = "groupTransaction") @Valid GroupTransaction grTransaction,
            BindingResult rs, @AuthenticationPrincipal MyUserDetails user) {

        GroupMember grMember = grMemberSer.getGroupMemberByUserIdAndGroupId(user.getId(), grTransaction.getGroupId().getId());
        grTransaction.setGroupMemberId(grMember);

        String msg = null;

        GroupExpense gr = groupSer.getGroupExpenseById(grTransaction.getGroupId().getId());
        Date now = new Date();

        if (now.compareTo(gr.getEndDate()) < 0) {
            if (!rs.hasErrors()) {
                if (grTransaction.getAmount() > 1000) {
                    if (grTransactionSer.addGroupTransaction(grTransaction) == true) {
                        return "redirect:/group/details/" + grTransaction.getGroupId().getId();
                    }
                } else {
                    model.addAttribute("errorMsg", "Số tiền thu chi phải lớn hơn 1000 đồng");
                }
            }
        }
        else{
            msg = "Kế hoạch nhóm đã kết thúc (không thể thêm thu chi mới)";
        }

        model.addAttribute("msg", msg);
        return "addTransactionGroup";
    }

    @GetMapping("/group/details/{id}/group-transaction")
    public String groupTransactionPage(Model model,
            @RequestParam Map<String, String> params,
            @PathVariable(value = "id") int id,
            @AuthenticationPrincipal MyUserDetails user) {

        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        Long countAccept = this.grTransactionSer.countGroupTransactionByGroupId(id, true);
        Long countNoAccept = this.grTransactionSer.countGroupTransactionByGroupId(id, false);

        model.addAttribute("counterAccept", Math.ceil(countAccept * 1.0 / pageSize));
        model.addAttribute("counterNoAccept", Math.ceil(countNoAccept * 1.0 / pageSize));

        Date now = new Date();
        model.addAttribute("currentDate", now);
        model.addAttribute("groupTransaction", new GroupTransaction());
        model.addAttribute("gr", this.groupSer.getGroupExpenseById(id));
        model.addAttribute("trGroupAccept", grTransactionSer.getGroupTransactionByGroupId(id, true, params));
        model.addAttribute("trGroupNoAccept", grTransactionSer.getGroupTransactionByGroupId(id, false, params));
        return "groupTransaction";
    }

    @PostMapping("/group/details/{id}/group-transaction")
    public String updateGroupTransaction(@ModelAttribute(value = "groupTransaction") GroupTransaction grTransaction) {
        if (grTransactionSer.updateIsActiveGroupTransaction(grTransaction.getId()) == true) {
            return "redirect:/group/details/{id}";
        }
        return "index";
    }
}
