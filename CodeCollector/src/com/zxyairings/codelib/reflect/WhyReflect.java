package com.zxyairings.codelib.reflect;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

/*
 * 我们在使用别人写得类，一般有两种方式：
 * 1. 我们写得类调用别人的类(工具类)
 * 2. 别人的类(框架)调用我们写得类
 * 
 * 
 * 反射的作用：实现框架功能
 * 
 * 什么是框架？
 * 		我做房子卖给用户住，有用户安装门窗和空调。我做的房子是框架，用户需要使用我的框架，把门窗插入进我提供的框架中。
 * 		框架与工具类有区别，工具类被用户的类调用，而框架则是调用用户提供的类。
 * 		你做的门调用锁，锁是工具类，你做的门将被房子调用，房子是框架，门是用户类。房子和锁都是别人提供的。
 * 
 * 框架需要解决的核心问题？
 * 		我在写框架（房子）时，你这个用户可能还在上小学，还不会写程序呢？我写的框架程序怎样能调用你以后写得类（门窗）呢？
 * 		因为在写程序时是无法知道要被调用的类名，所以，在程序中无法直接new某个类的实例对象了，而要用反射方式来做。
 * 
 * 
 * */

public class WhyReflect {
	
	public static void smallFrame() throws Exception {
		
		/*getRealPath();//金山词霸/内部
			一定要记住用完整的路径，但完整的路径不是硬编码，而是运算出来的。
		*/
		//这里使用的是相对路径，不好！
		//唯一的标准做法是上面的解释部分: new FileInputStream(getRealPath+"config.properties");	
		InputStream ips = new FileInputStream("config.properties");
		Properties props = new Properties();
		props.load(ips);
		ips.close();//关闭的是操作系统的资源，而java流对象ips则由垃圾回收在适当的时候回收掉
		String className = props.getProperty("className");
		Collection collections = (Collection)Class.forName(className).newInstance();
		
		ReflectPoint pt1 = new ReflectPoint(3,3);
		ReflectPoint pt2 = new ReflectPoint(5,5);
		ReflectPoint pt3 = new ReflectPoint(3,3);	

		collections.add(pt1);
		collections.add(pt2);
		collections.add(pt3);
		collections.add(pt1);	
		
		System.out.println(collections.size());
		
	}
	
	//如何加载资源文件
	public static void smallFramLoadRecource() throws Exception {
		
		//我们知道类加载器可以讲.class文件加载到内存中，那么它应该也提供了可以加载一般文件的功能：
		//getResourceAsStream 是在classPath根路径下去查找你指定的文件。如果不在根路径下，需要提供完整路径：
//		InputStream ips = WhyReflect.class.getClassLoader().getResourceAsStream("com/zxyairings/codelib/reflect/config.properties");//在路径最开始不能加/
		//使用类的字节码的提供的简便方法来加载，可以使用相对路径，或者绝对路径
//		InputStream ips = WhyReflect.class.getResourceAsStream("config.properties");//与上面的一句等价：但是使用的是相对路径，是相对于getResourceAsStream方法的字节码WhyReflect.class所在的包路径 com/zxyairings/codelib/reflect
//		InputStream ips = WhyReflect.class.getResourceAsStream("../resource/config.properties");//与上面的一句等价：但是使用的是相对路径，是相对于getResourceAsStream方法的字节码WhyReflect.class所在的包路径 com/zxyairings/codelib/reflect
		InputStream ips = WhyReflect.class.getResourceAsStream("/com/zxyairings/codelib/resource/config.properties"); //这里使用的是绝对路径,是开始与classpath的根路径

		
		Properties props = new Properties();
		props.load(ips);
		ips.close();
		String className = props.getProperty("className");
		Collection collections = (Collection)Class.forName(className).newInstance();
		
		ReflectPoint pt1 = new ReflectPoint(3,3);
		ReflectPoint pt2 = new ReflectPoint(5,5);
		ReflectPoint pt3 = new ReflectPoint(3,3);	

		collections.add(pt1);
		collections.add(pt2);
		collections.add(pt3);
		collections.add(pt1);	
		
		
		System.out.println(collections.size());
	}
	
	public static void main(String[] args) throws Exception{
//		smallFrame();//可对配置文件读写，是标准
		
		smallFramLoadRecource();
	}

}
