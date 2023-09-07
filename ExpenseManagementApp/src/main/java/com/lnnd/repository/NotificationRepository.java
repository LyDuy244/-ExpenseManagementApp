/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.repository;

import com.lnnd.pojo.Notification;

/**
 *
 * @author ADMIN
 */
public interface NotificationRepository {

    boolean addNotification(Notification noti);

    Notification getNotificationByInfo(int userId, int month, int quarter, int year, boolean type);

}
