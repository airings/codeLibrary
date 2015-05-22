package com.zxyairings.codelib.io.stream;

/*
用于操作字节数组的流对象。

ByteArrayInputStream ：在构造的时候，需要接收数据源，。而且数据源是一个字节数组。

ByteArrayOutputStream： 在构造的时候，不用定义数据目的，因为该对象中已经内部封装了可变长度的字节数组。
这就是数据目的地。

因为这两个流对象都操作的数组，并没有使用系统资源。
所以，不用进行close关闭。


在流操作规律讲解时：

源设备，
	键盘 System.in，硬盘 FileStream，内存 ArrayStream。
目的设备：
	控制台 System.out，硬盘FileStream，内存 ArrayStream。

对数组的操作有两种：对数据元素的get 和 set，可以对应于流中读和写。
用流的读写思想来操作数组。

相对应的操作内存的流有：
CharArrayReader
CharArrayWriter
	将内存对象-字节数组换成字符数组，从而更方便的操作字符数据
StringReader
StringWriter
	操作内存对象-字符串
	

*/
import java.io.*;
class ByteArrayStream 
{
	public static void main(String[] args) 
	{
		//数据源。源在内存当中
		ByteArrayInputStream bis = new ByteArrayInputStream("ABCDEFD".getBytes());

		//数据目的。目的在内存当中
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		int by = 0;

		while((by=bis.read())!=-1)
		{
			bos.write(by);
		}



		System.out.println(bos.size());
		System.out.println(bos.toString());

	//	bos.writeTo(new FileOutputStream("a.txt")); 此句有异常

	}
}
