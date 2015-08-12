package com.zxyairings.codelib.web.html;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RegServer {
	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(9090);
		Socket socket = ss.accept();
		System.out.println(socket.getInetAddress().getHostAddress());
		
		InputStream inputStream = socket.getInputStream();
		
		byte[] buf = new byte[1024];
		int len = inputStream.read(buf);
		
		System.out.println(new String(buf,0,len));
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		out.println("<font color='green' size='7'>注册成功</font>");
		
		socket.close();
		ss.close();
	}

}
