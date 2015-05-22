package com.zxyairings.codelib.network;

/*
 * 这个类就可以进行点对点的聊天了
 * 如果要群聊怎么办？
 * 在IP地址中有两个IP地址比较特殊：
 * 192.168.1.0 ： 代表一个网络段
 * 192.168.1.255：代表这个网络段里德广播地址。比如当找一个主机找不到时，可以在这个网络段里所有机器发广播。
 * 
 * 
 * */
import java.net.*;
import java.io.*;

public class UDPKeyBoardInput {

}



class  UdpSend2
{
	public static void main(String[] args) throws Exception
	{
		DatagramSocket ds = new DatagramSocket();

		BufferedReader bufr = 
			new BufferedReader(new InputStreamReader(System.in));

		String line = null;

		while((line=bufr.readLine())!=null)
		{
			if("886".equals(line))
				break;

			byte[] buf = line.getBytes();

			DatagramPacket dp = 
				new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.1.255"),10001);

			ds.send(dp);
		}
		ds.close();
	}
}



class  UdpRece2
{
	public static void main(String[] args) throws Exception
	{
		DatagramSocket ds = new DatagramSocket(10001);

		while(true)
		{
			byte[] buf = new byte[1024];
			DatagramPacket dp = new DatagramPacket(buf,buf.length);

			ds.receive(dp);

			String ip = dp.getAddress().getHostAddress();
			String data = new String(dp.getData(),0,dp.getLength());
			

			System.out.println(ip+"::"+data);
		}
	}
}
