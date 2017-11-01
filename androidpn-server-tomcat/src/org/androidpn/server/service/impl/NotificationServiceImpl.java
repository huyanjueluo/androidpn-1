/*
 * @(#)NotificationServiceImpl.java		       Project:Androidpn-tomcat
 * Date:2014年4月16日
 *
 * Copyright (c) 2014 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.server.service.impl;

import java.util.List;

import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.model.Notification;
import org.androidpn.server.service.NotificationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Geek_Soledad <a target="_blank" href=
 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=XTAuOSVzPDM5LzI0OR0sLHM_MjA"
 *         style="text-decoration:none;"><img src=
 *         "http://rescdn.qqmail.com/zh_CN/htmledition/images/function/qm_open/ico_mailme_01.png"
 *         /></a>
 */
public class NotificationServiceImpl implements NotificationService{

    protected final Log log = LogFactory.getLog(getClass());
    
    private NotificationDao notificationDao;
    
    public void setNotificationDao(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    public void saveNotification(Notification notification) {
        notificationDao.saveNotification(notification);
    }

    public void updateNotification(Notification notification) {
        notificationDao.updateNotification(notification);
    }

    public void deleteNotification(Long id) {
        notificationDao.deleteNotification(id);
    }

    public Notification queryNotificationById(Long id) {
        return notificationDao.queryNotificationById(id);
    }

    public void createNotifications(List<Notification> notifications) {
        for (Notification notification : notifications) {
            saveNotification(notification);
        }
    }

    public Notification queryNotificationByUsername(String username,
            String messageId) {
        List<Notification> notifications = notificationDao.queryNotificationByUsername(username, messageId);
        return notifications.isEmpty() ? null : notifications.get(0);
    }

    public List<Notification> queryNotification(Notification notification) {
        return notificationDao.queryNotification(notification);
    }
}
