/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.validator;

import com.lnnd.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author ADMIN
 */
@Component
public class RegisterConfirmPasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User u = (User) target;

        if (u.getConfirmPassword().equals("")) {
            errors.rejectValue("confirmPassword", "user.confirmPassword.notNull");
        } else {
            if (!u.getPassword().equals(u.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "user.confirmPassword.notMatch");
            }
        }
    }

}
