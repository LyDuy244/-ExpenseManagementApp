/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.Notification;
import com.lnnd.pojo.Transaction;
import com.lnnd.pojo.User;
import com.lnnd.service.NotificationService;
import com.lnnd.service.TransactionService;
import com.lnnd.service.TypeTransactionService;
import com.lnnd.service.UserService;
import com.lnnd.service.impl.MyUserDetails;
import com.lnnd.validator.GroupExpenseValidator;
import com.lnnd.validator.TransactionValidator;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
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

    @Autowired
    private NotificationService notiService;
    
    @Autowired
    private TransactionValidator transactionValidator;

    @InitBinder
    public void initBinder(WebDataBinder bider) {
        bider.setValidator(transactionValidator);
    }

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
        model.addAttribute("count", count);

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

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
        int quarter = (month - 1) / 3 + 1; // Tính quý từ tháng

        Locale vietnameseLocale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnameseLocale);

        if (!rs.hasErrors()) {
            if (tranSer.addOrUpdateTransaction(t) == true) {
                User u = userSer.getUserById(user.getId());
                Notification notificationMonth = this.notiService.getNotificationByInfo(user.getId(), month, 0, year, false);
                if (notificationMonth == null) {
                    long amountThisMonth = this.tranSer.getTotalAmountMonthOfTransactionsByUserId(user.getId(), "present");
                    long amountLastMonth = this.tranSer.getTotalAmountMonthOfTransactionsByUserId(user.getId(), "past");
                    if (amountThisMonth > amountLastMonth && amountLastMonth > 0) {
                        Notification noti = new Notification();
                        noti.setMonth(month);
                        noti.setQuarter(0);
                        noti.setYear(year);
                        noti.setUserId(u);
                        noti.setType(false);

                        // Định dạng số tiền sử dụng NumberFormat
                        if (notiService.addNotification(noti)) {
                            long totalAmount = amountThisMonth - amountLastMonth;
                            String formattedPrice = currencyFormatter.format(totalAmount);
                            this.userSer.sendMailWarning(u, "tháng này của quý khách đã vượt quá chi tiêu tháng trước (chi tiêu cá nhân): " + formattedPrice + " đồng. Xin hãy thu chi cẩn thận hơn");
                        }
                    }
                } else {
                    Notification notificationQuarter = this.notiService.getNotificationByInfo(user.getId(), 0, quarter, year, false);
                    if (notificationQuarter == null) {
                        long amountThisQuarter = this.tranSer.getTotalAmountQuarterOfTransactionsByUserId(user.getId(), "present");
                        long amountLastQuarter = this.tranSer.getTotalAmountQuarterOfTransactionsByUserId(user.getId(), "past");
                        if (amountThisQuarter - amountLastQuarter > 5000000 && amountLastQuarter > 0) {
                            Notification noti = new Notification();
                            noti.setMonth(0);
                            noti.setQuarter(quarter);
                            noti.setYear(year);
                            noti.setUserId(u);
                            noti.setType(false);

                            if (notiService.addNotification(noti)) {
                                long totalAmount = amountThisQuarter - amountLastQuarter;
                                String formattedPrice = currencyFormatter.format(totalAmount);
                                this.userSer.sendMailWarning(u, "quý này của quý khách đã vượt quá chi tiêu quý trước (chi tiêu cá nhân): " + formattedPrice + " đồng. Xin hãy thu chi cẩn thận hơn");
                            }
                        }
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
