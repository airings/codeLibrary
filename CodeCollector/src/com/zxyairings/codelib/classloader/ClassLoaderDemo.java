package com.zxyairings.codelib.classloader;

import java.util.Date;

/*
 * 系统默认三个主要类加载器，每个类负责加载特定位置的类：
 * BootStrap: 加载 JRE/lib/rt.jar
 * ExtClassLoader: 加载 JRE/lib/ext/*.jar
 * AppClassLoader: 加载 Classpath指定的所有jar或目录
 * 
 * 类加载器也是Java类，因为其他是java类的类加载器本身也要被类加载器加载，
 * 显然必须有第一个类加载器不是java类，这正是BootStrap，它是由c++写的。
 * 
 * 
 * Java虚拟机中的所有类装载器采用具有父子关系的树形结构进行组织，
 * 在实例化每个类装载器对象时，需要为其指定一个父级类装载器对象或者默认采用系统类装载器为其父级类加载。
 * 
 * 类加载器的委托机制：
 * 当Java虚拟机要加载一个类时，到底派出哪个类加载器去加载呢？
 * 首先当前线程的类加载器去加载线程中的第一个类。Thread.getContextClassLoader()
 * 如果类A中引用了类B，Java虚拟机将使用加载类A的类装载器来加载类B。
 * 还可以直接调用ClassLoader.loadClass()方法来指定某个类加载器去加载某个类。
 * 
 * 每个类加载器加载类时，又先委托给其上级类加载器。
 * 当所有祖宗类加载器没有加载到类，回到发起者类加载器，还加载不了，则抛ClassNotFoundException，
 * 不是再去找发起者类加载器的儿子，因为没有getChild方法，即使有，那有多个儿子，找哪一个呢？
 * 这种委托给父加载器加载类的机制的好处是：
 * 		类加载集中管理，不会出现多分字节码被多次加载的现象，因为某个加载器知道它加载过什么类，并且如果是它加载这个类那么总是这个类加载器加载这个类。
 * 
 * 面试题：能不能自己写个类叫java.lang.System?
 * 		java为了不让我们写system类，类加载采用委托机制，这样可以保证爸爸们优先，也就是总是使用爸爸们能找到的类，
 * 		这样总是使用java系统提供的system。永远不会加载自己写的system。但是可以自己写个类加载器，不要委托给父类加载器，来加载自己的system类。
 * 
 * */

public class ClassLoaderDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(
				ClassLoaderDemo.class.getClassLoader().getClass().getName()
				);
		System.out.println(
				System.class.getClassLoader()//BootStrap加载System
				);
		System.out.println("xxx");
		ClassLoader loader = ClassLoaderDemo.class.getClassLoader();
		while(loader != null){
			System.out.println(loader.getClass().getName());
			loader = loader.getParent();
		}
		System.out.println(loader);

		
		//有包名的类不能调用无包名的类
		//System.out.println(new ClassLoaderAttachment().toString());
		System.out.println("xxx2");
		Class clazz = new MyClassLoader("itcastlib").loadClass("cn.itcast.day2.ClassLoaderAttachment");
		Date d1 =  (Date)clazz.newInstance();
		System.out.println(d1);
	}

}