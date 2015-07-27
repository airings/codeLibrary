package com.zxyairings.codelib.network.tcp;


/*
 * 演示自己写个服务端，然后用不同的客户端来访问这个服务端，这个服务端仅仅是返回一个信息显示在客户端上
 * 
 * 
演示客户端和服务端。

1，第24天课程04 -- SelfDefinedServerDemo.java
客户端：浏览器/telnet
服务端：自定义。

2，第24天课程05
客户端：浏览器。
服务端：Tomcat服务器。Tomcat里面其实封装了ServerSocket，原理跟上面我们自己写的服务端是一样的。

3，第24天课程06-08 -- 06简单讲了HTTP协议的请求消息头   MyIE.java MyIEByGUI.java
客户端：自定义。(图形界面)
服务端：Tomcat服务器。



*/

import java.net.*;
import java.io.*;

//1，第24天课程04
//客户端：浏览器/telnet
//服务端：自定义。
class SelfDefinedServerDemo 
{
	public static void main(String[] args) throws Exception
	{
		ServerSocket ss = new ServerSocket(11000);

		Socket s = ss.accept();
		System.out.println(s.getInetAddress().getHostAddress());

		//打印客户端发给服务端的请求信息，其实就是HTTP协议的请求消息头
		InputStream in = s.getInputStream();
		byte[] buf = new byte[1024];
		int len = in.read(buf);
		System.out.println(new String(buf,0,len));


		PrintWriter out = new PrintWriter(s.getOutputStream(),true);

		//tomcat做的工作更这个类似，就是将htmlprintln打印到浏览器上显示
		out.println("<font color='red' size='7'>客户端你好</font>");

		s.close();

		ss.close();
	}
}
