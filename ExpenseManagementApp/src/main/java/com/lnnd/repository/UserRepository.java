/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository;

import com.lnnd.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface UserRepository {

    Long countUser();

    boolean addUser(User user);

    List<User> getUsers(String username);

    User getUserById(int id);

    boolean updateUser(int id, User user);

    List<User> getAllUser();

    List<User> getAllUserWithoutCurrentUser(int userId);

    List<User> getAllUserPagination(Map<String, String> params);

    User getUserByVerificationCode(String verificationCode);

    boolean isActive(int userId);

    boolean isBlock(int userId);
    
}
