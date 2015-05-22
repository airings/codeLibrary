package com.zxyairings.codelib.network;


import java.io.*;
import java.net.*;

/*
 * 还会出现无法结束的问题，原因是服务端的readLine方法没有读到结束标记
 * 有两种解决方法：
 * 1. 使用自定义的结束标记，比如"over"，比较好的是时间戳
 * 2. 使用Socket方法shutdownOutput
 * 
 */

class  TextClient
{
	public static void main(String[] args) throws Exception
	{
		Socket s = new Socket("192.168.1.254",10006);

		BufferedReader bufr = 
			new BufferedReader(new FileReader("IPDemo.java"));

//		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
//		long time = System.currentTimeMillis();
//		dos.writeLong(time);

		PrintWriter out = new PrintWriter(s.getOutputStream(),true);



		String line = null;
		while((line=bufr.readLine())!=null)
		{
			out.println(line);
		}

//		out.println("over");//结束标记
//		out.println(time);
		s.shutdownOutput();//关闭客户端的输出流。相当于给流中加入一个结束标记-1.

		
		BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));

		String str = bufIn.readLine();
		System.out.println(str);

		bufr.close();

		s.close();
	}
}
class  TextServer
{
	public static void main(String[] args) throws Exception
	{
		ServerSocket ss = new ServerSocket(10006);

		Socket s = ss.accept();
		String ip = s.getInetAddress().getHostAddress();
		System.out.println(ip+"....connected");

//		DataInputStream dis = new DataInputStream(s.getInputStream());
//		long time = dis.readLong(); //结束标记-时间戳

		BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));

		PrintWriter out  = new PrintWriter(new FileWriter("server.txt"),true);

		String line = null;

		while((line=bufIn.readLine())!=null)//无法结束
		{
			//if("over".equals(line))
				//break;
//			if(time==Long.parseLong(line))
//				break;//理解结束标记的方法，还有一些细节上要处理
			out.println(line);
		}

		PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
		pw.println("上传成功");


		out.close();
		s.close();
		ss.close();

	}
}
