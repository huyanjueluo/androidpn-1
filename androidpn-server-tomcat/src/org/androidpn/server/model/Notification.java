/*
 * @(#)Notification.java		       Project:Androidpn-tomcat
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
package org.androidpn.server.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消息的实体类。
 * 
 * @author Geek_Soledad <a target="_blank" href=
 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=XTAuOSVzPDM5LzI0OR0sLHM_MjA"
 *         style="text-decoration:none;"><img src=
 *         "http://rescdn.qqmail.com/zh_CN/htmledition/images/function/qm_open/ico_mailme_01.png"
 *         /></a>
 */
@Entity
@Table(name = "apn_notification")
public class Notification implements Serializable{

    private static final long serialVersionUID = -8185019956659917022L;

//    public static final int STATUS_NOT_SEND = 0;
//    public static final int STATUS_SEND = STATUS_NOT_SEND + 1;
//    public static final int STATUS_RECEIVED = STATUS_SEND + 1;
//    public static final int STATUS_READ = STATUS_RECEIVED +1;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "username", length = 64) 
    private String username;
    
    @Column(name = "clientIP", length = 64)
    private String clientIP;
    
    @Column(name = "resource", length = 64)
    private String resource;
    
    @Column(name = "messageId", length = 64)
    private String messageId;
    
    @Column(name = "apiKey", length =64)
    private String apiKey;
    
    @Column(name="title", length = 64)
    private String title;
    
    @Column(name = "message", length = 1024)
    private String message;
    
    @Column(name = "uri", length = 512)
    private String uri;
    
    @Column(name = "messageTime", length = 64)
    private String messageTime;
    
    @Enumerated
    @Column(name = "status")
    private Status status;
    
    @Column(name = "createTime", updatable = false)
    private Timestamp createTime;
    
    @Column(name = "updateTime")
    private Timestamp updateTime;

    public Notification() {
        super();
    }

    public Notification(String apiKey, String title, String message,
            String uri, String messageTime) {
        super();
        this.apiKey = apiKey;
        this.title = title;
        this.message = message;
        this.uri = uri;
        this.messageTime = messageTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    
    public static enum Status {
        /**
         * 未发送
         */
        NOT_SEND ,
        /**
         * 已发送
         */
        SEND,
        /**
         * 已收到
         */
        RECEIVED,
    }
    
}
