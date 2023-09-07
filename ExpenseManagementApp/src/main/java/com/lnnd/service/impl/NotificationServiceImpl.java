/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.service.impl;

import com.lnnd.pojo.Notification;
import com.lnnd.repository.NotificationRepository;
import com.lnnd.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public boolean addNotification(Notification noti) {
        return this.notificationRepository.addNotification(noti);
    }

    @Override
    public Notification getNotificationByInfo(int userId, int month, int quarter, int year, boolean type) {
        return this.notificationRepository.getNotificationByInfo(userId, month, quarter, year, type);
    }

}
