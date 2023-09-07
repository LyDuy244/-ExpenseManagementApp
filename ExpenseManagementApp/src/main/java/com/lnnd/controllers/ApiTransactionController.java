/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.Transaction;
import com.lnnd.service.TransactionService;
import com.lnnd.service.TypeTransactionService;
import com.lnnd.service.UserService;
import com.lnnd.service.impl.MyUserDetails;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api")
public class ApiTransactionController {

    @Autowired
    private TransactionService tranSer;
    @Autowired
    private TypeTransactionService typeTranSer;
    @Autowired
    private UserService userSer;

    @RequestMapping("/transactions/")
    @CrossOrigin
    public ResponseEntity<List<Transaction>> list(@RequestParam Map<String, String> params, 
            @AuthenticationPrincipal MyUserDetails user) {
        return new ResponseEntity<>(this.tranSer.getAllTransactionsByUserId(user.getId(), params, 10), HttpStatus.OK);
    }
    
    @DeleteMapping("/transactions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") int id){
        this.tranSer.deleteTransaction(id);
    }
    

    @PostMapping(path = "/transactions/", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.APPLICATION_JSON_VALUE
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestParam Map<String, String> params) {
        Transaction t = new Transaction();
        t.setPurpose(params.get("purpose"));
        t.setDescription(params.get("description"));
        t.setAmount(Long.parseLong(params.get("price")));
        t.setTypeId(this.typeTranSer.getTypeTransactionById(Integer.parseInt(params.get("categoryId"))));
        t.setUserId(this.userSer.getUserById(Integer.parseInt(params.get("userId"))));

//        product.setFile(file);
        this.tranSer.addOrUpdateTransaction(t);
    }
}
