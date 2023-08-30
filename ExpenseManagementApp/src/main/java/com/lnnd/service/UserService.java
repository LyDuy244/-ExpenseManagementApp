/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service;

import com.lnnd.pojo.GroupExpense;
import com.lnnd.pojo.User;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author ADMIN
 */
public interface UserService extends UserDetailsService {

    Long countUser();

    boolean addUser(User user);

    List<User> getUsers(String username);

    User getUserById(int id);

    boolean updateUser(int id, User user);

    void sendMailWarning(User user, String text) throws MessagingException, UnsupportedEncodingException;

    List<User> getAllUser();

    List<User> getAllUserWithoutCurrentUser(int userId);

    void sendVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException;

    User getUserByVerificationCode(String verifyCode);

    boolean isActive(int userId);

    boolean isBlock(int userId);

    boolean verify(String verify);

    void sendInviteEmail(User userFrom, User userTo, GroupExpense group, String siteUrl) throws MessagingException, UnsupportedEncodingException;

    List<User> getAllUserPagination(Map<String, String> params);
}
