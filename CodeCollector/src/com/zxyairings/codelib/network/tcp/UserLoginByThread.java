package com.zxyairings.codelib.network.tcp;

/*
 * 客户端并发登陆
 * 
客户端通过键盘录入用户名。
服务端对这个用户名进行校验。

如果该用户存在，在服务端显示xxx，已登陆。
并在客户端显示 xxx，欢迎光临。

如果该用户存在，在服务端显示xxx，尝试登陆。
并在客户端显示 xxx，该用户不存在。

最多就登录三次。
*/
import java.io.*;
import java.net.*;


class  LoginClient
{
	public static void main(String[] args) throws Exception
	{
		Socket s = new Socket("192.168.1.254",10008);

		BufferedReader bufr = 
			new BufferedReader(new InputStreamReader(System.in));

		PrintWriter out = new PrintWriter(s.getOutputStream(),true);

		BufferedReader bufIn =
			new BufferedReader(new InputStreamReader(s.getInputStream()));


		for(int x=0; x<3; x++)
		{
			String line = bufr.readLine();
			if(line==null)//ctrl+c就可以把null发给服务端
				break;
			out.println(line);

			String info = bufIn.readLine();
			System.out.println("info:"+info);
			if(info.contains("欢迎"))
				break; 

		}

		bufr.close();
		s.close();
	}
}


class UserThread implements Runnable
{
	private Socket s;
	UserThread(Socket s)
	{
		this.s = s;
	}
	public void run()
	{
		String ip = s.getInetAddress().getHostAddress();
		System.out.println(ip+"....connected");
		try
		{
			for(int x=0; x<3; x++)
			{
				BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));

				String name = bufIn.readLine();
				if(name==null)
					break;

				BufferedReader bufr = new BufferedReader(new FileReader("user.txt"));

				PrintWriter out = new PrintWriter(s.getOutputStream(),true);

				String line = null;

				//要的是循环判断后的结果，那么必然是在循环外判断，故需要一个标记来记录循环判断的结果
				boolean flag = false;
				while((line=bufr.readLine())!=null)
				{
					if(line.equals(name))
					{
						flag = true;
						break;
					}				
				}
				
				if(flag)
				{
					System.out.println(name+",已登录");
					out.println(name+",欢迎光临");
					break;
				}
				else
				{
					System.out.println(name+",尝试登录");
					out.println(name+",用户名不存在");
				}


			}
			s.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(ip+"校验失败");
		}
	}
}
class  LoginServer
{
	public static void main(String[] args) throws Exception
	{
		ServerSocket ss = new ServerSocket(10008);

		while(true)
		{
			Socket s = ss.accept();

			new Thread(new UserThread(s)).start();
		}
	}
}
