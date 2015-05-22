package com.zxyairings.codelib.collections;



/*
Properties是hashtable的子类。
也就是说它具备map集合的特点。而且它里面存储的键值对都是字符串。不需要泛型。

是集合中和IO技术相结合的集合容器。

该对象的特点：可以用于键值对形式的配置文件。

那么在加载数据时，需要数据有固定格式：键=值。



练习：限制程序运行次数。当运行次数到达5次时，给出，请您注册的提示。并不再让该程序执行。


*/
import java.io.*;
import java.util.*;

class PropertiesDemo 
{
	public static void main(String[] args) throws IOException
	{
		//method_1();
		loadDemo();
	}

	public static void loadDemo()throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("info.txt");

		//将流中的数据加载进集合。
		prop.load(fis);
//		System.out.println(prop);
//		prop.list(System.out);

		prop.setProperty("wangwu","39");

		FileOutputStream fos = new FileOutputStream("info.txt");

		prop.store(fos,"haha");

	//	System.out.println(prop);
		prop.list(System.out);

		fos.close();
		fis.close();

	}

	//演示，如何将流中的数据存储到集合中。
	//想要将info.txt中键值数据存到集合中进行操作。
	// 是上面load方法的原理
	/*
		1,用一个流和info.txt文件关联。
		2，读取一行数据，将该行数据用"="进行切割。
		3，等号左边作为键，右边作为值。存入到Properties集合中即可。

	*/
	public static void method_1()throws IOException
	{
		BufferedReader bufr = new BufferedReader(new FileReader("info.txt"));

		String line = null;
		Properties prop = new Properties();


		while((line=bufr.readLine())!=null)
		{
			String[] arr = line.split("=");
			///System.out.println(arr[0]+"...."+arr[1]);
			prop.setProperty(arr[0],arr[1]);
		}

		bufr.close();

		System.out.println(prop);
	}



//	设置和获取元素。
	public static void setAndGet()
	{
		Properties prop = new Properties();

		//SET
		prop.setProperty("zhangsan","30");
		prop.setProperty("lisi","39");
//		System.out.println(prop);
		
		//GET
		String value = prop.getProperty("lisi");
		//System.out.println(value);
			
		prop.setProperty("lisi",89+"");

		//遍历
		Set<String> names = prop.stringPropertyNames();
		for(String s : names)
		{
			System.out.println(s+":"+prop.getProperty(s));
		}
	}

	
}
