/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.GroupMember;
import com.lnnd.pojo.GroupTransaction;
import com.lnnd.pojo.Notification;
import com.lnnd.pojo.User;
import com.lnnd.service.GroupExpenseService;
import com.lnnd.service.GroupMemberService;
import com.lnnd.service.GroupTransactionService;
import com.lnnd.service.NotificationService;
import com.lnnd.service.UserService;
import com.lnnd.service.impl.MyUserDetails;
import com.lnnd.validator.GroupTransactionValidator;
import com.lnnd.validator.TransactionValidator;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
public class TransactionGroupController {

    @Autowired
    private Environment env;

    @Autowired
    private GroupExpenseService groupSer;

    @Autowired
    private GroupTransactionService grTransactionSer;

    @Autowired
    private UserService userSer;

    @Autowired
    private NotificationService notiService;

    @Autowired
    private GroupMemberService grMemberSer;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private GroupTransactionValidator groupTransactionValidator;

    @InitBinder("groupTransaction")
    public void initGroupTransactionBinder(WebDataBinder binder) {
        binder.setValidator(groupTransactionValidator);
    }

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

    @GetMapping("/group/details/{id}/update/{grTranId}")
    public String updateGroupTransactionPage(Model model,
            @PathVariable(value = "id") int id,
            @PathVariable(value = "grTranId") int grTranId,
            @AuthenticationPrincipal MyUserDetails user) {

        GroupMember grMember = grMemberSer.getGroupMemberByUserIdAndGroupId(user.getId(), id);
        Date now = new Date();

        model.addAttribute("gr", this.groupSer.getGroupExpenseById(id));
        model.addAttribute("groupTransaction", this.grTransactionSer.getGroupTransactionById(grTranId));
        model.addAttribute("memberGroup", grMember);
        model.addAttribute("currentDate", now);

        return "addTransactionGroup";
    }

    @PostMapping("/group/details/add-transaction")
    public String addGroupTransaction(Model model,
            @ModelAttribute(value = "groupTransaction") @Valid GroupTransaction grTransaction, Locale locale,
            BindingResult rs, @AuthenticationPrincipal MyUserDetails user) {

        GroupMember grMember = grMemberSer.getGroupMemberByUserIdAndGroupId(user.getId(), grTransaction.getGroupId().getId());
        grTransaction.setGroupMemberId(grMember);

        String msg = null;

        GroupExpense gr = groupSer.getGroupExpenseById(grTransaction.getGroupId().getId());
        Date now = new Date();

        if (now.compareTo(gr.getEndDate()) < 0) {
            if (!rs.hasErrors()) {
                if (grTransaction.getId() != null) {
                    if (grTransactionSer.updateGroupTransaction(grTransaction.getId(), grTransaction) == true) {
                        return "redirect:/group/details/" + grTransaction.getGroupId().getId();

                    }
                } else {
                    if (grTransactionSer.addGroupTransaction(grTransaction) == true) {
                        return "redirect:/group/details/" + grTransaction.getGroupId().getId();
                    }
                }
            }
        } else {
            msg = messageSource.getMessage("alert.end.group.transaction", null, locale);
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
        Long countGrTransaction = this.grTransactionSer.countGroupTransactionByGroupIdAndUserId(id, user.getId());

        model.addAttribute("counterAccept", Math.ceil(countAccept * 1.0 / pageSize));
        model.addAttribute("counterNoAccept", Math.ceil(countNoAccept * 1.0 / pageSize));
        model.addAttribute("counterTran", Math.ceil(countGrTransaction * 1.0 / pageSize));

        Date now = new Date();
        model.addAttribute("currentDate", now);
        model.addAttribute("groupTransaction", new GroupTransaction());
        model.addAttribute("gr", this.groupSer.getGroupExpenseById(id));
        model.addAttribute("trGroup", this.grTransactionSer.getGroupTransactionByGroupIdAndUserId(id, user.getId(), params));
        model.addAttribute("trGroupAccept", this.grTransactionSer.getGroupTransactionByGroupId(id, true, params));
        model.addAttribute("trGroupNoAccept", this.grTransactionSer.getGroupTransactionByGroupId(id, false, params));
        return "groupTransaction";
    }

    @PostMapping("/group/details/{id}/group-transaction")
    public String updateGroupTransaction(@ModelAttribute(value = "groupTransaction") GroupTransaction grTransaction,
            @PathVariable(value = "id") int id,
            Model model, Locale locale) throws MessagingException, UnsupportedEncodingException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
        int quarter = (month - 1) / 3 + 1; // Tính quý từ tháng

        Locale vietnameseLocale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnameseLocale);
        GroupTransaction groupTransaction = grTransactionSer.getGroupTransactionById(grTransaction.getId());
        String msg = null;

        GroupExpense gr = groupTransaction.getGroupId();
        Date now = new Date();

        if (now.compareTo(gr.getEndDate()) < 0) {

            if (grTransactionSer.updateIsActiveGroupTransaction(grTransaction.getId()) == true) {

                User u = groupTransaction.getGroupMemberId().getUserId();
                Notification notificationMonth = this.notiService.getNotificationByInfo(u.getId(), month, 0, year, true);
                if (notificationMonth == null) {
                    long amountThisMonth = this.grTransactionSer.getTotalAmountMonthOfGroupTransactionsByUserId(u.getId(), "present");
                    long amountLastMonth = this.grTransactionSer.getTotalAmountMonthOfGroupTransactionsByUserId(u.getId(), "past");
                    if (amountThisMonth - amountLastMonth > 5000000 && amountLastMonth > 0) {
                        Notification noti = new Notification();
                        noti.setMonth(month);
                        noti.setQuarter(0);
                        noti.setYear(year);
                        noti.setUserId(u);
                        noti.setType(true);

                        // Định dạng số tiền sử dụng NumberFormat
                        if (notiService.addNotification(noti)) {
                            long totalAmount = amountThisMonth - amountLastMonth;
                            String formattedPrice = currencyFormatter.format(totalAmount);
                            String msg1 = messageSource.getMessage("mail.alert.group.transaction.month.msg", null, locale);
                            String msg2 = messageSource.getMessage("mail.alert.end", null, locale);
                            this.userSer.sendMailWarning(u, msg1 + formattedPrice + msg2);
                        }
                    }
                } else {
                    Notification notificationQuarter = this.notiService.getNotificationByInfo(u.getId(), 0, quarter, year, true);
                    if (notificationQuarter == null) {
                        long amountThisQuarter = this.grTransactionSer.getTotalAmountQuarterOfGroupTransactionsByUserId(u.getId(), "present");
                        long amountLastQuarter = this.grTransactionSer.getTotalAmountQuarterOfGroupTransactionsByUserId(u.getId(), "past");
                        if (amountThisQuarter - amountLastQuarter > 10000000 && amountLastQuarter > 0) {
                            Notification noti = new Notification();
                            noti.setMonth(0);
                            noti.setQuarter(quarter);
                            noti.setYear(year);
                            noti.setUserId(u);
                            noti.setType(true);

                            if (notiService.addNotification(noti)) {
                                long totalAmount = amountThisQuarter - amountLastQuarter;
                                String formattedPrice = currencyFormatter.format(totalAmount);
                                String msg1 = messageSource.getMessage("mail.alert.group.transaction.quarter.msg", null, locale);
                                String msg2 = messageSource.getMessage("mail.alert.end", null, locale);
                                this.userSer.sendMailWarning(u, msg1 + formattedPrice + msg2);
                            }
                        }
                    }
                }
            }
            return "redirect:/group/details/{id}";

        } else {
            msg = messageSource.getMessage("alert.end.group.transaction", null, locale);
        }
        model.addAttribute("msg", msg);
        return "redirect:/group/details/{id}/group-transaction";
    }
}
