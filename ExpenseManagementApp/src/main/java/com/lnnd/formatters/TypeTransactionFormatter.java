/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.formatters;

import com.lnnd.pojo.TypeTransaction;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ADMIN
 */
public class TypeTransactionFormatter implements Formatter<TypeTransaction>{

    @Override
    public String print(TypeTransaction type, Locale locale) {
        return String.valueOf(type.getId());
    }

    @Override
    public TypeTransaction parse(String typeId, Locale locale) throws ParseException {
        int id = Integer.parseInt(typeId);
        return new TypeTransaction(id);
    }
    
}
