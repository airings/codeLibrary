package com.zxyairings.codelib.others;

import java.util.Properties;

/*
System:类中的方法和属性都是静态的。
out:标准输出，默认是控制台。
in：标准输入，默认是键盘。


描述系统一些信息。

获取系统属性信息：Properties getProperties();
*/

class SystemDemo 
{
	public static void main(String[] args) 
	{
		System.currentTimeMillis();
		

		Properties prop = System.getProperties();
		
		//因为Properties是Hashtable的子类，也就是Map集合的一个子类对象。
		//那么可以通过map的方法取出该集合中的元素。
		//该集合中存储都是字符串。没有泛型定义。
		//获取所有属性信息。
		for(Object obj : prop.keySet())
		{
			String value2 = (String)prop.get(obj);

			System.out.println(obj+"::"+value2);
		}
				
		//如何在系统中自定义一些特有信息呢？
		System.setProperty("mykey","myvalue");



		//获取指定属性信息。  
		String value = System.getProperty("os.name");

		System.out.println("value="+value);

		

		//可不可以在jvm启动时，动态加载一些属性信息呢？
		// we know starting a jvm uses java command 
		// java -D<name>=<value>: set a system property
		// java -Dhaha=qqqqq SystemDemo
		// this will set "haha" a value "qqqqq" when starting jvm
		String v = System.getProperty("haha");

		System.out.println("v="+v); //v=null;


		

		
	}
}