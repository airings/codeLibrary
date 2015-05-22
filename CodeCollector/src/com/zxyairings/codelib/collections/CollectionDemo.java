package com.zxyairings.codelib.collections;

import java.util.*;

/*
Collection定义了集合框架的共性功能。
1，添加
	add(e);
	addAll(collection);

2，删除
	remove(e);
	removeAll(collection);
	clear();

3，判断。
	contains(e);
	isEmpty();

4，获取
	iterator();
	size();

5，获取交集。
	retainAll();

6，集合变数组。
	toArray();



1，add方法的参数类型是Object。以便于接收任意类型对象。

2，集合中存储的都是对象的引用(地址), 而不是真的对象实体


什么是迭代器呢？
其实就是集合的取出元素的方式。而取出的方式不足以用一个方法来描述，需要用多个功能来体现，所以封装成一个取出对象，
而且集合框架中的各个实现类的数据结构不同，而取出方法的实现方式也不尽相同，所以可以将其抽取为接口。
为什么使用内部类？
就把取出方式定义在集合的内部(内部类)，这样取出方式就可以直接访问集合内容的元素。

那么取出方式就被定义成了内部类

而每一个容器的数据结构不同，所以取出的动作细节也不一样，但是都有共性的地方：判断和取出。那么可以将共性抽取。
那么这些内部类都符合一个规则，该规则就是Iterator接口，如何获取集合的取出对象呢？通过一个对外提供的方法iterator()

如同抓娃娃游戏机中的夹子。

迭代器是取出方式，会直接访问集合中的元素。
所以将迭代器通过内部类的形式来进行描述。
通过容器的iterator()方法获取该内部类的对象。

*/
class  CollectionDemo
{
	public static void main(String[] args) 
	{
		
		method_get();
	}
	public static void method_get()
	{
		ArrayList al = new ArrayList();

		//1，添加元素。
		al.add("java01");//add(Object obj);
		al.add("java02");
		al.add("java03");
		al.add("java04");

		/*
		Iterator it = al.iterator();//获取迭代器，用于取出集合中的元素。

		while(it.hasNext())
		{
			sop(it.next());
		}
		*/

		//这样写的话 it 是局部变量使用完会释放资源，比较正规。
		for(Iterator it = al.iterator(); it.hasNext() ; )
		{
			sop(it.next());
		}
	}


	public static void method_2()
	{
		ArrayList al1 = new ArrayList();

		al1.add("java01");
		al1.add("java02");
		al1.add("java03");
		al1.add("java04");
		ArrayList al2 = new ArrayList();

		al2.add("java03");
		al2.add("java04");
		al2.add("java05");
		al2.add("java06");

		
		//al1.retainAll(al2);//取交集，al1中只会保留和al2中相同的元素。会改变集合al1
		al1.removeAll(al2);//取差集, al1-al2 = al1 & !al2

		sop("al1:"+al1);
		sop("al2:"+al2);




	}

	public static void base_method()
	{
		//创建一个集合容器。使用Collection接口的子类。ArrayList
		ArrayList al = new ArrayList();

		//1，添加元素。
		al.add("java01");//add(Object obj);
		al.add("java02");
		al.add("java03");
		al.add("java04");

		//打印原集合。
		sop("原集合:"+al);


		//3，删除元素。
		//al.remove("java02");
		//al.clear();//清空集合。


		//4，判断元素。
		sop("java03是否存在:"+al.contains("java03"));
		sop("集合是否为空？"+al.isEmpty());


		//2，获取个数。集合长度。
		sop("size:"+al.size());

		//打印改变后的集合。
		sop(al);

	}

	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
}
