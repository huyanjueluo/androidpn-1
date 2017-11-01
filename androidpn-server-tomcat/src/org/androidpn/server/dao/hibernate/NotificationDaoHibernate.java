/*
 * @(#)NotificationDaoHibernate.java		       Project:Androidpn-tomcat
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
package org.androidpn.server.dao.hibernate;

import java.util.List;

import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.model.Notification;
import org.androidpn.server.model.Notification.Status;
import org.androidpn.server.model.Receipt;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Geek_Soledad <a target="_blank" href=
 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=XTAuOSVzPDM5LzI0OR0sLHM_MjA"
 *         style="text-decoration:none;"><img src=
 *         "http://rescdn.qqmail.com/zh_CN/htmledition/images/function/qm_open/ico_mailme_01.png"
 *         /></a>
 */
public class NotificationDaoHibernate extends HibernateDaoSupport implements NotificationDao{

    public void saveNotification(Notification notification) {
        getHibernateTemplate().saveOrUpdate(notification);
        getHibernateTemplate().flush();
    }

    public void updateNotification(Notification notification) {
        getHibernateTemplate().update(notification);
        getHibernateTemplate().flush();
    }

    public void deleteNotification(Long id) {
        getHibernateTemplate().delete(queryNotificationById(id));
    }

    public Notification queryNotificationById(Long id) {
        return (Notification) getHibernateTemplate().get(Notification.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Notification> queryNotificationByUsername(String username,
            String messageId) {
        Object[] params = new Object[]{username, messageId};
        return getHibernateTemplate().find("from Notification n where n.username=? and n.messageId=? order by n.createTime desc", params);
    }

    public int queryContByStatus(Status status, String messageId) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressWarnings("unchecked")
    public List<Notification> queryNotification(Notification notification) {
        return getHibernateTemplate().findByExample(notification);
    }
    
    public List<Receipt> queryReceipts(Notification notification) {
        // TODO Auto-generated method stub
        return null;
    }

}
