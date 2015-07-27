package com.zxyairings.codelib.network;
//可以用来建立连接 注意这个连接是在应用层，所以它的输入流中没有请求消息头
import java.net.*;
import java.io.*;
class  URLConnectionDemo
{
	public static void main(String[] args) throws Exception
	{
		URL url = new URL("http://192.168.1.254:8080/myweb/demo.html");

		//这样就直接连接出去了，不用再写socket了，因为之前是在传输层，现在是在应用层, 因为现在是http协议了
		URLConnection conn = url.openConnection();
		System.out.println(conn);
		
		InputStream in = conn.getInputStream(); 

		byte[] buf = new byte[1024];

		int len = in.read(buf);

		System.out.println(new String(buf,0,len));
	}
}
