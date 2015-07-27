package com.zxyairings.codelib.network;

/*
 * URL 除了可以解析url的作用，还可以建立连接，它封装了socket连接，详细见URLConnectionDemo.java
 */

import java.net.*;
class URLDemo 
{
	public static void main(String[] args) throws MalformedURLException
	{
		URL url = new URL("http://192.168.1.254/myweb/demo.html?name=haha&age=30");


		System.out.println("getProtocol() :"+url.getProtocol());
		System.out.println("getHost() :"+url.getHost());
		System.out.println("getPort() :"+url.getPort());
		System.out.println("getPath() :"+url.getPath());
		System.out.println("getFile() :"+url.getFile());
		System.out.println("getQuery() :"+url.getQuery());

		/*int port = getPort();没有指定端口号，会返回-1，这时一般会指定一个默认端口80
		if(port==-1)
			port = 80;

		getPort()==-1
		*/
	}
}
/*
 String getFile() 
          获取此 URL 的文件名。 
 String getHost() 
          获取此 URL 的主机名（如果适用）。 
 String getPath() 
          获取此 URL 的路径部分。 
 int getPort() 
          获取此 URL 的端口号。 
 String getProtocol() 
          获取此 URL 的协议名称。 
 String getQuery() 
          获取此 URL 的查询部 

*/