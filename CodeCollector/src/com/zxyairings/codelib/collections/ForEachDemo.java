package com.zxyairings.codelib.collections;

/*
高级for循环

格式：
for(数据类型 变量名 : 被遍历的集合(Collection)或者数组)
{
	
}

对集合进行遍历。此升级是为了简化书写。
此foreach语句只能获取集合元素。但是不能对集合进行操作。

迭代器除了遍历，还可以进行remove集合中元素的动作。
如果是用ListIterator，还可以在遍历过程中对集合进行增删改查的动作。

传统for和高级for有什么区别呢？

高级for有一个局限性。必须有被遍历的目标。

建议：
在遍历数组的时候，还是希望是用传统for。因为传统for可以定义脚标。




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


java 1.4 Collection 接口中有iterator 方法，返回一个迭代器。
但是java 1.5版本 Coolection 接口新增加了他的父接口 Iterable, 此接口就是将iterator方法单独抽取出来了，提高了java 集合框架的可扩展性。此外，实现这个接口的对象，就可以成为foreach语句作用目标。



*/

import java.util.*;

class ForEachDemo 
{
	public static void main(String[] args) 
	{

		
		ArrayList<String> al = new ArrayList<String>();

		al.add("abc1");
		al.add("abc2");
		al.add("abc3");


		for(String s : al)
		{
			//s = "kk";
			System.out.println(s);
		}

		System.out.println(al);
		/*
		Iterator<String> it = al.iterator();

		while(it.hasNext())
		{
			System.out.println(it.next());
		}
		*/

		int[] arr = {3,5,1};

		for(int x=0; x<arr.length; x++)
		{
			System.out.println(arr[x]);
		}
		for(int i : arr)
		{
			System.out.println("i:"+i);
		}


		HashMap<Integer,String> hm = new HashMap<Integer,String>();

		hm.put(1,"a");
		hm.put(2,"b");
		hm.put(3,"c");

		Set<Integer> keySet = hm.keySet();
		for(Integer i : keySet)
		{
			System.out.println(i+"::"+hm.get(i));
		}

//		Set<Map.Entry<Integer,String>> entrySet = hm.entrySet();
//		for(Map.Entry<Integer,String> me : entrySet)

		for(Map.Entry<Integer,String> me : hm.entrySet())
		{
			System.out.println(me.getKey()+"------"+me.getValue());
		}

	}
}
