/*
 * @(#)NotificationDao.java		       Project:Androidpn-tomcat
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
package org.androidpn.server.dao;

import java.util.List;

import org.androidpn.server.model.Notification;
import org.androidpn.server.model.Receipt;
import org.androidpn.server.model.Notification.Status;

/**
 * 通知 DAO。
 * 
 * @author Geek_Soledad <a target="_blank" href=
 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=XTAuOSVzPDM5LzI0OR0sLHM_MjA"
 *         style="text-decoration:none;"><img src=
 *         "http://rescdn.qqmail.com/zh_CN/htmledition/images/function/qm_open/ico_mailme_01.png"
 *         /></a>
 */
public interface NotificationDao {

    /**
     * 保存通知消息。
     * @param notification
     */
    public void saveNotification(Notification notification);
    
    /**
     * 更新通知消息。
     * @param notification
     */
    public void updateNotification(Notification notification);
    
    /**
     * 删除通知消息。
     * @param id
     */
    public void deleteNotification(Long id);
    
    /**
     * 通过ID 查询通知消息。
     * @param id
     * @return
     */
    public Notification queryNotificationById(Long id);
    
    /**
     * 通过用户名和通知id查询消息。
     * @param username
     * @param messageId
     * @return
     */
    public List<Notification> queryNotificationByUsername(String username, String messageId);
    
    /**
     * 通过状态和消息id查询消息。
     * @param status
     * @param messageId
     * @return
     */
    public int queryContByStatus(Status status, String messageId);
    
    /**
     * 通过条件查询消息。
     * @param notification
     * @return
     */
    public List<Notification> queryNotification(Notification notification);
    
    public List<Receipt> queryReceipts(Notification notification);
}
