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
	 * ��ȡ��ָ��ʱ�俪ʼʧЧ���豸��
	 * 
	 * @param online
	 *            false=��ʾ��ȡʧЧ�û���true=��ʾ��ȡ�����û�
	 * @param startTime
	 *            ��ʼʱ�䣬��ʽ�磺2012-04-12 15:04:55
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
	 * ����ָ���û�
	 * 
	 * @param title
	 *            ֪ͨ����
	 * @param message
	 *            ֪ͨ����
	 * @param username
	 *            �û�username
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
		parameter.append(""); // �����͸�iPhone��ʽ����һ��
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
		parameter.append(""); // �����͸�iPhone��ʽ����һ��
		String resp = GetPostUtil.send("POST", URL + "notification_api.do", parameter);
		return resp;
	}

	/**
	 * ���������û�
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
		parameter.append(""); // �����͸�iPhone��ʽ����һ��
		String resp = GetPostUtil.send("POST", URL + "notification_api.do", parameter);
		return resp;
	}
}
