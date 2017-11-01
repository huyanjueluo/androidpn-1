/*
 * @(#)CopyMessageUtil.java		       Project:Androidpn-tomcat
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
package org.androidpn.server.util;

import org.androidpn.server.model.Notification;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.xmpp.packet.IQ;

/**
 * @author Geek_Soledad <a target="_blank" href=
 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=XTAuOSVzPDM5LzI0OR0sLHM_MjA"
 *         style="text-decoration:none;"><img src=
 *         "http://rescdn.qqmail.com/zh_CN/htmledition/images/function/qm_open/ico_mailme_01.png"
 *         /></a>
 */
public class CopyMessageUtil {

    public static void IQ2Message(IQ iq, Notification notification) {
        Element root = iq.getElement();
        Attribute idAttr = root.attribute("id");
        if(idAttr != null) {
            notification.setMessageId(idAttr.getValue());
        }
    }
}
