ANDROIDPN README
=======================
http://androidpn.sourceforge.net/

Push Notification Service for Android

This is an open source project to provide push notification support for Android
-- a xmpp based notification server and a client tool kit. 


----
###v0.8更新
1. 增加发送离线消息功能
2. 增加回执功能。


###v0.7.1更新(2014-04-10 09:57:45):
1、服务端session被移除导致掉线的BUG
<br/>
2、客户端service会被内存清理工具停止的BUG
<br/>
3、锁屏后会掉线的问题
<br/>
4、连接成功后，重连次数不清零问题（导致下次重连的时间较大）

###v0.7.0更新：

新功能：

**androidpn-client-androidstudio**

1、对消息进行序列化，可直接通过intent传递对象
<br/>
2、可通过配置文件配置IQ,IQProvider, PacketListener的子类实现，及打开通知时弹出的Activity。
<br/>
3、配置的host如果为域名，可以转换为IP
<br/>
4、消息增加时间

**androidpn-server-bin-tomcat**

1、消息增加时间



###v0.6.0更新后16:36 2012/4/13

此开源项目包含三个部分：
1、Android客户端示例
2、消息推送服务器端示例（源码、jetty版本、tomcat版本）
3、项目可以直接引用的中间件


文件描述：
androidpn-client：android客户端工程源码包
androidpn-server-src：androidpn服务器端源码包
androidpn-server-bin-jetty：androidpn服务器端jetty版本
androidpn-server-bin-tomcat：androidpn服务器端tomcat版本，可直接绑定数据库(如Mysql)
apnsUnit：中间件。项目中可以直接引用来调用服务器端消息推送接口


--------------------------------------------------
开始前请准备：

androidpn-client：
1、修改res/raw/androidpn.properties配置文件
把xmppHost=127.0.0.1改成你自己的ip；模拟器设置为：10.0.2.2


androidpn-server-bin-jetty：
修改配置文件conf/config.properties
admin.console.host=10.62.1.226为服务器的ip地址


androidpn-server-bin-tomcat：
1、用到了java的spring框架
2、修改resources/jdbc.properties配置文件
3、查看WebRoot/WEB-INF/dispatcher-servlet.xml配置文件
4、访问地址为；http://127.0.0.1:8080/ 端口号为tomcat所使用的端口号


--------------------------------------------------
新功能：

androidpn-server
新添加了两个接口：
package org.androidpn.server.console.api;
UserApiController.java 用于获取用户列表，并返回json数据；
NotificationApiController.java 用户消息推送


WebRoot/WEB-INF/dispatcher-servlet.xml文件更新
添加了
/user_api.do=userapiController	
/notification_api.do=notificationapiController

为防止别人恶意用网页群发消息推送，以下可隐藏  
/index.do=filenameController  
/user.do=userController  
/session.do=sessionController  
/notification.do=notificationController  

--------------------------------------------------
修复的BUG列表：
androidpn-client：
1、重复发送消息时，被覆盖的问题。
    用示例程序，我连续推送2条消息，无论点击哪一条，都显示最新的那条数据。（对不上号）
    例如，我先推送标题为“A”的新闻，然后再推送标题为‘B’的新闻，这个时候，客户端收到两条推送消息，分别是A、B，无论我点击A，还是B，详情页都显示B的信息

2014-4-2：

2、修复页面路径错误(androidpn-server-bin-tomcat)
<br/>
3、更正日志文件名字拼写错误(androidpn-server-bin-tomcat)
<br/>
4、修复程序卸载重装，或清除数据时，会重新注册新用户名的问题(androidpn-client-androidstudio)
