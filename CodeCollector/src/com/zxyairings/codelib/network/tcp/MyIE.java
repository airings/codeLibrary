package com.zxyairings.codelib.network.tcp;

import java.io.*;
import java.net.*;

//自定义客户端，发给tomcat

//3，第24天课程06 -- 简单讲了HTTP协议的请求消息头   MyIE.java
//客户端：自定义。(命令行)
//服务端：Tomcat服务器。

class MyIE 
{
	public static void main(String[] args)throws Exception 
	{
		Socket s = new Socket("192.168.1.254",8080);
		
		PrintWriter out = new PrintWriter(s.getOutputStream(),true);

		out.println("GET /myweb/demo.html HTTP/1.1");
		out.println("Accept: */*");
		out.println("Accept-Language: zh-cn");
		out.println("Host: 192.168.1.254:11000");
		out.println("Connection: closed");

		out.println();//一定要写空行
		out.println();

		BufferedReader bufr = new BufferedReader(new InputStreamReader(s.getInputStream()));

		String line = null;

		while((line=bufr.readLine())!=null)
		{
			System.out.println(line);
		}

		s.close();




	}
}
/*
 * 浏览器发给服务端的请求消息头

http://192.168.1.254:11000/myweb/demo.html
<请求消息头>
GET(请求方式) /myweb/demo.html(资源路径和要请求的资源) HTTP/1.1(协议版本)
Accept: application/x-shockwave-flash, image/gif, image/x-xbitmap, image/jpeg, i
mage/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, application
/msword, application/QVOD, application/QVOD,
Accept-Language: zh-cn
Accept-Encoding: gzip, deflate
User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0
.50727)
Host: 192.168.1.254:11000
Connection: Keep-Alive
<空行> ： 请求消息头和请求数据体用一个空行分隔
<请求数据体>
*/
