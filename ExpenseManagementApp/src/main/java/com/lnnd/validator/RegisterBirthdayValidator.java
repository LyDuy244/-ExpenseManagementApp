/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.validator;

import com.lnnd.pojo.User;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author ADMIN
 */
@Component
public class RegisterBirthdayValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User u = (User) target;
        LocalDate currentDate = LocalDate.now();

        if (u.getBirthday() != null) {
            Instant instant = u.getBirthday().toInstant();
            LocalDate birthday = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            long yearsDifference = ChronoUnit.YEARS.between(birthday, currentDate);

            if (yearsDifference < 18) {
                errors.rejectValue("birthday", "user.birthday.tooYoung");
            }
        }
    }

}
