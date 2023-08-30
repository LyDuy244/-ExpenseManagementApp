/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.validator;

import com.lnnd.pojo.User;
import com.lnnd.service.UserService;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author ADMIN
 */
@Component
public class RegisterUsernameValidate implements Validator{
    private final UserService userDetailsService;

    public RegisterUsernameValidate(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User u = (User) target;

        List<User> users = this.userDetailsService.getAllUser();

        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                if (user.getUsername().equals(u.getUsername())) {
                    errors.rejectValue("username", "user.name.exists");
                }
            }
        }
    }
}
