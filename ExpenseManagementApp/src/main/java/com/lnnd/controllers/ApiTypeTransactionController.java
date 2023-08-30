/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.controllers;

import com.lnnd.pojo.TypeTransaction;
import com.lnnd.service.TypeTransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api")
public class ApiTypeTransactionController {
    @Autowired
    public TypeTransactionService typeTranSer;
    
    @RequestMapping(value = "/typeTransaction/", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseEntity<List<TypeTransaction>> list() {
        return new ResponseEntity<>(this.typeTranSer.getTypeTransaction(), HttpStatus.OK);
    }
}
