package com.zxyairings.codelib.resource;


//Serializable 接口没有要实现的方法，这种接口又称为标记接口。
//其实就是给这个类盖个章，标记后就具有资格来序列化，反序列化

import java.io.*;
public class Person implements Serializable
{
	//类产生对象，此对象持久化后，这个类可能发生改变，编译后产生的序列号与对象的序列号不匹配
	//所以就可以用这个序列号来判断：对象与类之间是否匹配
	//这个id号是由类的成员算来的
	//如果声明下面UID则id号固定不变
	public static final long serialVersionUID = 42L;

	private String name;
	// 非静态变量可以用 transient 来阻止序列化
	transient int age;
	// 静态型无法序列化，静态在方法区。我们只能序列化堆中的对象
	static String country = "cn";
	public  Person(String name,int age,String country)
	{
		this.name = name;
		this.age = age;
		this.country = country;
	}
	public String toString()
	{
		return name+":"+age+":"+country;
	}
}
