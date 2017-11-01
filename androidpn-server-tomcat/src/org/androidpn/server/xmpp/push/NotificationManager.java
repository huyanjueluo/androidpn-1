/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.push;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.androidpn.server.model.Notification;
import org.androidpn.server.model.Notification.Status;
import org.androidpn.server.model.User;
import org.androidpn.server.service.NotificationService;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserService;
import org.androidpn.server.util.CopyMessageUtil;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

/**
 * This class is to manage sending the notifcations to the users.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationManager {

	private static final String ELEM_URI = "uri";

	private static final String ELEM_MESSAGE = "message";

	private static final String ELEM_TITLE = "title";

	private static final String ELEM_API_KEY = "apiKey";

	private static final String ELEM_ID = "id";

	private static final String ELEM_TIME = "time";

	private static final String NOTIFICATION_NAMESPACE = "androidpn:iq:notification";

	private final Log log = LogFactory.getLog(getClass());

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH时mm分");

	private SessionManager sessionManager;

	private NotificationService notificationService;

	private UserService userService;

	/**
	 * Constructor.
	 */
	public NotificationManager() {
		sessionManager = SessionManager.getInstance();
		notificationService = ServiceLocator.getNotificationService();
		userService = ServiceLocator.getUserService();
	}

	/**
	 * 发送通知给所有的用户（包括不在线的用户）
	 * 
	 * @param apiKey
	 *            the API key
	 * @param title
	 *            the title
	 * @param message
	 *            the message details
	 * @param uri
	 *            the uri
	 */
	public void sendBroadcastToAll(String apiKey, String title, String message, String uri) {
		// TODO
		String time = dateFormat.format(Calendar.getInstance().getTime());
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri, time);
		List<User> list = userService.getUsers();
		List<Notification> notifications = new ArrayList<Notification>();
		for (User user : list) {
			ClientSession session = sessionManager.getSession(user.getUsername());
			Notification notification = new Notification(apiKey, title, message, uri, time);
			notification.setUsername(user.getUsername());
			CopyMessageUtil.IQ2Message(notificationIQ, notification);
			if (session != null && session.getPresence().isAvailable()) {
				notificationIQ.setTo(session.getAddress());
				session.deliver(notificationIQ);
				notification.setStatus(Status.SEND);
				try {
					notification.setClientIP(session.getHostAddress());
					notification.setResource(session.getAddress().getResource());
				} catch (Exception e) {
					log.error("unknown host", e);
				}
			} else {
				notification.setStatus(Status.NOT_SEND);
			}
			notifications.add(notification);
		}
		notificationService.createNotifications(notifications);
	}

	/**
	 * Broadcasts a newly created notification message to all connected users.
	 * 
	 * @param apiKey
	 *            the API key
	 * @param title
	 *            the title
	 * @param message
	 *            the message details
	 * @param uri
	 *            the uri
	 */
	public void sendBroadcastToOnline(String apiKey, String title, String message, String uri) {
		log.debug("sendBroadcast()...");

		List<Notification> notifications = new ArrayList<Notification>();

		String time = dateFormat.format(Calendar.getInstance().getTime());
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri, time);
		for (ClientSession session : sessionManager.getSessions()) {
			Notification notification = new Notification(apiKey, title, message, uri, time);
			try {
				notification.setUsername(session.getUsername());
				notification.setClientIP(session.getHostAddress());
				notification.setResource(session.getAddress().getResource());
			} catch (Exception e) {
				log.error("sendBroadcast error", e);
			}

			CopyMessageUtil.IQ2Message(notificationIQ, notification);
			if (session.getPresence().isAvailable()) {
				notificationIQ.setTo(session.getAddress());
				session.deliver(notificationIQ);
				notification.setStatus(Status.SEND);
			} else {
				notification.setStatus(Status.NOT_SEND);
			}
			notifications.add(notification);
		}
		notificationService.createNotifications(notifications);
	}

	/**
	 * Sends a newly created notification message to the specific user.
	 * 
	 * @param apiKey
	 *            the API key
	 * @param title
	 *            the title
	 * @param message
	 *            the message details
	 * @param uri
	 *            the uri
	 */
	public void sendNotifcationToUser(String apiKey, String username, String title, String message, String uri) {
		log.debug("sendNotifcationToUser()...");
		String time = dateFormat.format(Calendar.getInstance().getTime());
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri, time);
		ClientSession session = sessionManager.getSession(username);
		Notification notification = new Notification(apiKey, title, message, uri, time);
		notification.setUsername(username);
		CopyMessageUtil.IQ2Message(notificationIQ, notification);
		if (session != null && session.getPresence().isAvailable()) {
			notificationIQ.setTo(session.getAddress());
			session.deliver(notificationIQ);
			notification.setStatus(Status.SEND);
			try {
				notification.setClientIP(session.getHostAddress());
				notification.setResource(session.getAddress().getResource());
			} catch (Exception e) {
				log.error("unknown host", e);
			}
		} else {
			notification.setStatus(Status.NOT_SEND);
		}
		notificationService.saveNotification(notification);
	}

	public void sendNotifcationToUserMultiple(String apiKey, String[] usernames, String title, String message,
			String uri) {
		log.debug("sendNotifcationToUserMultiple()...");

		List<Notification> notifications = new ArrayList<Notification>();

		String time = dateFormat.format(Calendar.getInstance().getTime());
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri, time);
		for (String username : usernames) {
			ClientSession session = sessionManager.getSession(username);
			Notification notification = new Notification(apiKey, title, message, uri, time);
			notification.setUsername(username);

			CopyMessageUtil.IQ2Message(notificationIQ, notification);
			if (session != null && session.getPresence().isAvailable()) {
				notificationIQ.setTo(session.getAddress());
				session.deliver(notificationIQ);
				notification.setStatus(Status.SEND);
				try {
					notification.setClientIP(session.getHostAddress());
					notification.setResource(session.getAddress().getResource());
				} catch (Exception e) {
					log.error("unknown host", e);
				}
			} else {
				notification.setStatus(Status.NOT_SEND);
			}
			notifications.add(notification);
		}
		notificationService.createNotifications(notifications);
	}

	/**
	 * Creates a new notification IQ and returns it.
	 */
	private IQ createNotificationIQ(String apiKey, String title, String message, String uri, String time) {
		Random random = new Random();
		String id = Integer.toHexString(random.nextInt());
		// String id = String.valueOf(System.currentTimeMillis());

		Element notification = DocumentHelper.createElement(QName.get("notification", NOTIFICATION_NAMESPACE));
		notification.addElement(ELEM_ID).setText(id);
		notification.addElement(ELEM_API_KEY).setText(apiKey);
		notification.addElement(ELEM_TITLE).setText(title);
		notification.addElement(ELEM_MESSAGE).setText(message);
		notification.addElement(ELEM_URI).setText(uri);
		notification.addElement(ELEM_TIME).setText(time);

		IQ iq = new IQ();
		iq.setType(IQ.Type.set);
		iq.setChildElement(notification);
		System.out.println(iq.getChildElement().asXML().toString());

		return iq;
	}

	public void sendOfflineNotification(Notification notification) {
		log.debug("sendOfflineNotification()...");
		IQ notificationIQ = createNotificationIQ(notification.getApiKey(), notification.getTitle(),
				notification.getMessage(), notification.getUri(), notification.getMessageTime());
		notificationIQ.setID(notification.getMessageId());
		ClientSession session = sessionManager.getSession(notification.getUsername());
		if (session != null && session.getPresence().isAvailable()) {
			notificationIQ.setTo(session.getAddress());
			session.deliver(notificationIQ);
			try {
				notification.setStatus(Status.SEND);
				notification.setClientIP(session.getHostAddress());
				notification.setResource(session.getAddress().getResource());
			} catch (UnknownHostException e) {
				log.error("unknown host ", e);
			}
			notificationService.updateNotification(notification);
		}
	}
}
