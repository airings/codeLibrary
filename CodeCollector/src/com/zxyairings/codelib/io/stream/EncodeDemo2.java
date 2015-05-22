package com.zxyairings.codelib.io.stream;

import java.util.Arrays;

class EncodeDemo2 
{
	public static void main(String[] args) throws Exception
	{
		String s = "联通";
		//“联通”的GBK编码的二进制形式跟utf-8的编码形式一致，所以在记事本在解码时使用了utf-8，所以会有乱码的问题。
		//为了让记事本使用GBK解码，在“联通”前加入任何一个中国字就可以了

		byte[] by = s.getBytes("gbk");//编码

		System.out.println(Arrays.toString(by));
		for(byte b : by)
		{
			System.out.println(b);
			System.out.println(Integer.toBinaryString(b));
			System.out.println(Integer.toBinaryString(b&255));
		}
	}
}
