/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.validator;

import com.lnnd.pojo.GroupTransaction;
import com.lnnd.pojo.Transaction;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author ADMIN
 */
@Component
public class GroupTransactionValidator implements Validator{
    @Autowired  
    private javax.validation.Validator beanValidator;
    
    private Set<Validator> springValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return GroupTransaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> bean = this.beanValidator.validate(target);
        for (ConstraintViolation<Object> obj:bean){
            errors.rejectValue(obj.getPropertyPath().toString(), obj.getMessageTemplate(), obj.getMessage());
        }
        
        for (Validator v : this.springValidator) {
            v.validate(target, errors);
        }
    }

    /**
     * @param springValidator the springValidator to set
     */
    public void setSpringValidator(Set<Validator> springValidator) {
        this.springValidator = springValidator;
    }
}
