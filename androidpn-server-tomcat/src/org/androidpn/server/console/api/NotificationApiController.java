//
//  NotificationApiController.java
//  FeOA
//
//  Created by LuTH on 2012-4-1.
//  Copyright 2012 flyrise. All rights reserved.
//

package org.androidpn.server.console.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class NotificationApiController extends MultiActionController {

	private NotificationManager notificationManager;

	public NotificationApiController() {
		notificationManager = new NotificationManager();
	}

	public void send(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String broadcast = ServletRequestUtils.getStringParameter(request, "broadcast", "Y");
		String username = ServletRequestUtils.getStringParameter(request, "username");
		String title = ServletRequestUtils.getStringParameter(request, "title");
		String message = ServletRequestUtils.getStringParameter(request, "message");
		String uri = ServletRequestUtils.getStringParameter(request, "uri");

		String apiKey = Config.getString("apiKey", "");
		logger.debug("apiKey=" + apiKey);

		if (broadcast.equalsIgnoreCase("A")) {
			notificationManager.sendBroadcastToAll(apiKey, title, message, uri);
		} else if (broadcast.equalsIgnoreCase("N")) {
			if (!username.contains("，") && !username.contains(",")) {
				notificationManager.sendNotifcationToUser(apiKey, username, title, message, uri);
			} else {
				if (username.contains("，")) {
					username = username.replaceAll("，", ",");
				}
				String[] usernames = username.split(",");
				notificationManager.sendNotifcationToUserMultiple(apiKey, usernames, title, message, uri);
			}
		}

		response.getWriter().print("{\"result\":\"0\",\"description\":\"success\"}");
	}

	public void sendSingle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = ServletRequestUtils.getStringParameter(request, "username");
		String title = ServletRequestUtils.getStringParameter(request, "title");
		String message = ServletRequestUtils.getStringParameter(request, "message");
		String uri = ServletRequestUtils.getStringParameter(request, "uri");
		String apiKey = Config.getString("apiKey", "");

		logger.debug("apiKey=" + apiKey);
		notificationManager.sendNotifcationToUser(apiKey, username, title, message, uri);

		response.getWriter().print("{\"result\":\"0\",\"description\":\"success\"}");
	}

	public void sendMultiple(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] usernames = ServletRequestUtils.getStringParameters(request, "usernames");
		String title = ServletRequestUtils.getStringParameter(request, "title");
		String message = ServletRequestUtils.getStringParameter(request, "message");
		String uri = ServletRequestUtils.getStringParameter(request, "uri");
		String apiKey = Config.getString("apiKey", "");

		logger.debug("apiKey=" + apiKey);
		notificationManager.sendNotifcationToUserMultiple(apiKey, usernames, title, message, uri);

		response.getWriter().print("{\"result\":\"0\",\"description\":\"success\"}");
	}
}
