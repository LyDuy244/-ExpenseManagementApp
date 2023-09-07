/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lnnd.service;

import com.lnnd.pojo.Notification;

/**
 *
 * @author ADMIN
 */
public interface NotificationService {

    boolean addNotification(Notification noti);

    Notification getNotificationByInfo(int userId, int month, int quarter, int year, boolean type);
}
