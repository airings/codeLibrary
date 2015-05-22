package com.zxyairings.codelib.generic;


import java.util.*;
/*
? 通配符。也可以理解为占位符。
泛型的限定；
？ extends E: 可以接收E类型或者E的子类型。上限。
？ super E: 可以接收E类型或者E的父类型。下限

*/


class  GenericWildcard
{
	public static void main(String[] args) 
	{
		
		ArrayList<String> al = new ArrayList<String>();

		al.add("abc1");
		al.add("abc2");
		al.add("abc3");

		ArrayList<Integer> al1 = new ArrayList<Integer>();
		al1.add(4);
		al1.add(7);
		al1.add(1);

		printColl(al);
		printColl(al1);


	}
	//下面两个方法等价
	//  ?代表 不明确类型，是个占位符，不能操作具体类型数据
	public static void printColl(ArrayList<?> al)//ArrayList al = new ArrayList<Integer>();error
	{
		Iterator<?> it = al.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next().toString());//toString()是可以的，因为他是通用的。
//			System.out.println(it.next().length());// 不明确类型不能调用具体类型的特有方法。
		}
	}
	// 使用T，就代表是个具体的类型，可以操作具体数据，T t = it.next();
	public static <T> void printColl1(ArrayList<T> al)//ArrayList al = new ArrayList<Integer>();error
	{
		Iterator<T> it = al.iterator();
		while(it.hasNext())
		{
			T t = it.next();
			System.out.println(t);
		}
	}
	
}


