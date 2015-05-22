package com.zxyairings.codelib.network;

import java.net.*;

public class UDPDemo {

}

//从发送端和接收端都有端口号可以看出，其实端口号就是网络应用程序的数字标示，用来标示在同一ip地址上不同的应用程序。



/*
需求：通过udp传输方式，将一段文字数据发送出去。，
定义一个udp发送端。
思路：
1，建立updsocket服务。--- 就相当于要先找到一个邮局
2，提供数据，并将数据封装到数据包中。
3，通过socket服务的发送功能，将数据包发出去。
4，关闭系统资源。因为至少它在使用网卡资源

*/


class  UdpSend
{
	public static void main(String[] args) throws Exception
	{
		//1，创建udp服务。通过DatagramSocket对象。
		//其实发送端也有端口号，如果没有指定，系统就会随机分配
		DatagramSocket ds = new DatagramSocket(8888);

		//2，确定数据，并封装成数据包。DatagramPacket(byte[] buf, int length, InetAddress address, int port) 

		byte[] buf = "udp ge men lai le ".getBytes();
		DatagramPacket dp = 
			new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.1.254"),10000);

		//3，通过socket服务，将已有的数据包发送出去。通过send方法。
		ds.send(dp);

		//4，关闭资源。

		ds.close();

	}
}

/*
需求：
定义一个应用程序，用于接收udp协议传输的数据并处理的。


定义udp的接收端。
思路：
1，定义udpsocket服务。通常会监听一个端口。其实就是给这个接收网络应用程序定义数字标识。
	方便于明确哪些数据过来该应用程序可以处理。

2，定义一个数据包，因为要存储接收到的字节数据。
因为数据包对象中有更多功能可以提取字节数据中的不同数据信息。
3，通过socket服务的receive方法将收到的数据存入已定义好的数据包中。
4，通过数据包对象的特有功能。将这些不同的数据取出。打印在控制台上。
5，关闭资源。

*/


class  UdpRece
{
	public static void main(String[] args) throws Exception
	{
		//1,创建udp socket，建立端点。
		//如果没有指定，系统会随机分配一个端口给socket。这里指定了10000
		DatagramSocket ds = new DatagramSocket(10000);//如果将这句话放入while-ture中会有java.net.BindException: Address already in use 端口绑定异常，因为在10000端口上尝试建立多个不同的socket对象。
		while(true)
		{
		//2,定义数据包。用于存储数据。
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf,buf.length);

		//3，通过服务的receive方法将收到数据存入数据包中。
		//接收端的receive是阻塞式方法，没有数据我就等，其实就是线程机制：wait，等数据发过来了，就notify 了。
		ds.receive(dp);
		

		//4，通过数据包的方法获取其中的数据。
		String ip = dp.getAddress().getHostAddress();

		String data = new String(dp.getData(),0,dp.getLength());

		int port = dp.getPort();

		System.out.println(ip+"::"+data+"::"+port);

		}
		//5，关闭资源
		//ds.close();

	}
}
