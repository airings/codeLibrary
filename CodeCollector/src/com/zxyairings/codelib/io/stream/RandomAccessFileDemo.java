package com.zxyairings.codelib.io.stream;

import java.io.*;


/*
RandomAccessFile

该类不是算是IO体系中子类。
而是直接继承自Object。

但是它是IO包中成员。因为它具备读和写功能。
内部封装了一个数组，而且通过指针对数组的元素进行操作。
可以通过getFilePointer获取指针位置，
同时可以通过seek改变指针的位置。
 
操作数据必定使用流。
其实完成读写的原理就是内部封装了字节输入流和输出流。

通过构造函数可以看出，该类只能操作文件。
而且操作文件还有模式：只读r，，读写rw等。

如果模式为只读 r。不会创建文件。会去读取一个已存在文件，如果该文件不存在，则会出现异常。
如果模式rw。操作的文件不存在，会自动创建。如果存则不会覆盖。

可以用来实现多线程写文件，一个线程负责一段数据
*/
class RandomAccessFileDemo 
{
	public static void main(String[] args) throws IOException
	{
		//writeFile_2();
		//readFile();
 
		//System.out.println(Integer.toBinaryString(258));

	}

	public static void readFile()throws IOException
	{
		RandomAccessFile raf = new RandomAccessFile("ran.txt","r");
		
		//调整对象中指针。
		//raf.seek(8*1);

		//跳过指定的字节数
		raf.skipBytes(8);

		byte[] buf = new byte[4];

		raf.read(buf);

		String name = new String(buf);

		int age = raf.readInt();


		System.out.println("name="+name);
		System.out.println("age="+age);

		raf.close();


	}

	public static void writeFile_2()throws IOException
	{
		RandomAccessFile raf = new RandomAccessFile("ran.txt","rw");
		raf.seek(8*0);
		raf.write("周期".getBytes());
		raf.writeInt(103);

		raf.close();
	}

	public static void writeFile()throws IOException
	{
		RandomAccessFile raf = new RandomAccessFile("ran.txt","rw");

		raf.write("李四".getBytes());
		raf.writeInt(97);
		raf.write("王五".getBytes());
		raf.writeInt(99);

		raf.close();
	}
}
