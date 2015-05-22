package com.zxyairings.codelib.generic;


import java.util.*;
/*
? 通配符。也可以理解为占位符。wildcard
泛型的限定；bounded wildcard
？ extends E: 可以接收E类型或者E的子类型。上限。因为E定了，E是父类，所以是上限
？ super E: 可以接收E类型或者E的父类型。下限

*/


class  GenericBoundedWildcard
{
	public static void main(String[] args) 
	{

		ArrayList<Person1> al = new ArrayList<Person1>();
		al.add(new Person1("abc1"));
		al.add(new Person1("abc2"));
		al.add(new Person1("abc3"));
		//printColl(al);

		ArrayList<Student2> al1 = new ArrayList<Student2>();
		al1.add(new Student2("abc--1"));
		al1.add(new Student2("abc--2"));
		al1.add(new Student2("abc--3"));
		//printColl(al1);  //ArrayList<Person1> al = new ArrayList<Student2>();error
		printColl1(al1);  //Yes!
		//ArrayList<? extends Person1> al = new ArrayList<Student2>();error

	}
	
	public static void printColl(ArrayList<Person1> al)
	{
		Iterator<Person1> it = al.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next().getName());
		}
	}
	// 泛型限定
	public static void printColl1(ArrayList<? extends Person1> al)
	{
		Iterator<? extends Person1> it = al.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next().getName());
		}
	}
/*
	public static void printColl(Collection<? extends Person1> al)
	{
		Iterator<? extends Person1> it = al.iterator();


		while(it.hasNext())
		{
			System.out.println(it.next().getName());
		}
	}
	*/
}

class Person1
{
	private String name;
	Person1(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
}

class Student2 extends Person1
{
	Student2(String name)
	{
		super(name);
	}

}


/*

class Student2 implements Comparable<Person1>//<? super E>
{
	public int compareTo(Person1 s)
	{
		this.getName()
	}
}


class Comp implements Comparator<Person1>
{
	public int compare(Person1 s1,Person1 s2)
	{

		//Person1 s1 = new Student2("abc1");
		return s1.getName().compareTo(s2.getName());
	}
}

TreeSet<Student2> ts = new TreeSet<Student2>(new Comp());
ts.add(new Student2("abc1"));
ts.add(new Student2("abc2"));
ts.add(new Student2("abc3"));


*/



