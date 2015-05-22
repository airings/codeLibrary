package com.zxyairings.codelib.network;

import java.net.*;
import java.util.Iterator;

class  IPDemo
{
	public static void main(String[] args) throws Exception
	{
		//InetAddress i = InetAddress.getLocalHost();

//		System.out.println(i.toString());
//		System.out.println("address:"+i.getHostAddress());
//		System.out.println("name:"+i.getHostName());

		InetAddress ia = InetAddress.getByName("www.baidu.com");
		System.out.println("address:"+ia.getHostAddress());
		System.out.println("name:"+ia.getHostName());
		
		InetAddress[] names = Inet4Address.getAllByName("www.baidu.com");
		
		for(InetAddress i :names){
			System.out.println(i.toString());
		}



	}
}
