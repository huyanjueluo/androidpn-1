package com.contorn.androidpn.service.client;

import java.net.URLEncoder;

public final class AndroidpnServerClient {

	private static final String URL = "http://10.7.3.170:8080/";
	private static AndroidpnServerClient instance;

	public static AndroidpnServerClient getInstance() {
		// return instance;
		if (instance == null) {
			synchronized (AndroidpnServerClient.class) {
				instance = new AndroidpnServerClient();
			}
		}
		return instance;
	}

	private AndroidpnServerClient() {
	}

	/**
	 * 获取从指定时间开始失效的设备号
	 * 
	 * @param online
	 *            false=表示获取失效用户；true=表示获取在线用户
	 * @param startTime
	 *            开始时间，格式如：2012-04-12 15:04:55
	 * @return {"data":["b737c188-513e-3546-8952-b74f37136c35",
	 *         "e509a0a4-cea6-3cff-a9b0-0e98a36c5fd4"]}
	 */
	public String getUser(boolean online, String startTime) {
		@SuppressWarnings("deprecation")
		String tiem = URLEncoder.encode(startTime);
		String response = GetPostUtil.sendGet(URL + "user_api.do", "from=" + tiem + "&online=" + (online ? 1 : 0));
		return response;
	}

	/**
	 * 推送指定用户
	 * 
	 * @param title
	 *            通知标题
	 * @param message
	 *            通知内容
	 * @param username
	 *            用户username
	 * @return
	 */
	public String sendSingle(String title, String message, String username) {
		final StringBuilder parameter = new StringBuilder();
		parameter.append("action=sendSingle");
		parameter.append("&username=");
		parameter.append(username);
		parameter.append("&title=");
		parameter.append(title);
		parameter.append("&message=");
		parameter.append(message);
		parameter.append("&uri=");
		parameter.append(""); // 和推送给iPhone格式保持一致
		String resp = GetPostUtil.send("POST", URL + "notification_api.do", parameter);
		return resp;
	}

	public String sendMultiple(String title, String message, String... usernames) {
		final StringBuilder parameter = new StringBuilder();
		parameter.append("action=sendMultiple");
		if (usernames != null && usernames.length > 0) {
			for (String username : usernames) {
				parameter.append("&usernames=");
				parameter.append(username);
			}
		}
		parameter.append("&title=");
		parameter.append(title);
		parameter.append("&message=");
		parameter.append(message);
		parameter.append("&uri=");
		parameter.append(""); // 和推送给iPhone格式保持一致
		String resp = GetPostUtil.send("POST", URL + "notification_api.do", parameter);
		return resp;
	}

	/**
	 * 推送所有用户
	 * 
	 * @param title
	 * @param message
	 * @return
	 */
	public String sendAll(String title, String message) {
		final StringBuilder parameter = new StringBuilder();
		parameter.append("action=send&broadcast=A");
		parameter.append("&title=");
		parameter.append(title);
		parameter.append("&message=");
		parameter.append(message);
		parameter.append("&uri=");
		parameter.append(""); // 和推送给iPhone格式保持一致
		String resp = GetPostUtil.send("POST", URL + "notification_api.do", parameter);
		return resp;
	}
}
