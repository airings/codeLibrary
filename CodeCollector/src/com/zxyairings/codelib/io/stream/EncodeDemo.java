package com.zxyairings.codelib.io.stream;


/*
编码：字符串变成字节数组。


解码：字节数组变成字符串。

String-->byte[];  str.getBytes(charsetName);


byte[] -->String: new String(byte[],charsetName);

*/
import java.util.*;
class  EncodeDemo
{
	public static void main(String[] args) throws Exception 
	{
		String s = "你好";

		byte[] b1 = s.getBytes("GBK");//编码

		System.out.println(Arrays.toString(b1));
		
		String s1 = new String(b1,"iso8859-1");//解码
		System.out.println("s1="+s1);

		//对s1进行iso8859-1编码。如果这时替换使用utf-8，这种再编码在解码的方式会出错，原因是GBK和utf-8都识别中文
		byte[] b2 = s1.getBytes("iso8859-1");
		System.out.println(Arrays.toString(b2));

		String s2 = new String(b2,"gbk");

		System.out.println("s2="+s2);

		

	}
}
