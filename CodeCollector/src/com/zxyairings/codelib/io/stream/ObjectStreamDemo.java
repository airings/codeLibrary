package com.zxyairings.codelib.io.stream;

//Serializable 接口没有要实现的方法，这种接口又称为标记接口。
//其实就是给这个类盖个章，标记后就具有资格来序列化，反序列化

import java.io.*;
import com.zxyairings.codelib.resource.Person;

class ObjectStreamDemo 
{
	public static void main(String[] args) throws Exception
	{
		//writeObj();
		readObj();
	}
	public static void readObj()throws Exception
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("obj.txt"));

		Person p = (Person)ois.readObject();

		System.out.println(p);
		ois.close();
	} 

	public static void writeObj()throws IOException
	{
		ObjectOutputStream oos = 
			new ObjectOutputStream(new FileOutputStream("obj.txt"));

		oos.writeObject(new Person("lisi0",399,"kr"));

		oos.close();
	}
}
