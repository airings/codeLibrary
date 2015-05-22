package com.zxyairings.codelib.io.stream;

/*
DataInputStream与DataOutputStream

可以用于操作基本数据类型的数据的流对象。

*/
import java.io.*;
class DataStreamDemo 
{
	public static void main(String[] args) throws IOException
	{
		//writeData();
		//readData();

		//writeUTFDemo();

//		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("gbk.txt"),"gbk");
//
//		osw.write("你好");
//		osw.close();

//		readUTFDemo();

	}
	public static void readUTFDemo()throws IOException
	{
		DataInputStream dis = new DataInputStream(new FileInputStream("utf.txt"));

		String s = dis.readUTF();

		System.out.println(s);
		dis.close();
	}



	public static void writeUTFDemo()throws IOException
	{
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("utfdate.txt"));

		dos.writeUTF("你好");

		dos.close();
	}

	public static void readData()throws IOException
	{
		DataInputStream dis = new DataInputStream(new FileInputStream("data.txt"));

		int num = dis.readInt();
		boolean b = dis.readBoolean();
		double d = dis.readDouble();

		System.out.println("num="+num);
		System.out.println("b="+b);
		System.out.println("d="+d);

		dis.close();
	}
	public static void writeData()throws IOException
	{
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.txt"));

		dos.writeInt(234);
		dos.writeBoolean(true);
		dos.writeDouble(9887.543);

		dos.close();

		ObjectOutputStream oos = null;
		oos.writeObject(new O());

		
	}
}
