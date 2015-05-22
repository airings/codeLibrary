package com.zxyairings.codelib.io.pattern;


/*
多线程下的单例
如果多线程的run方法中使用了单例的getInstance方法，那么会不会有线程安全问题？
不管是饿汉式还是懒汉式，共享数据是存在的，那就是s
*/


//饿汉式：它不存在线程安全问题。因为虽然s是共享数据，但是操作它的只有一句话return s;所以不会有线程安全问题
/*
class Single
{
	private static final Single s = new Single();
	private Single(){}
	public static Single getInstance()
	{
		return s;
	}
}
*/

//懒汉式:也叫延迟加载单例设计模式
//有共享数据s，并且有多条语句来操作它，所以它存在安全问题

//加入同步为了解决多线程安全问题。

//加入双重判断是为了解决效率问题。如果把getInstance变成同步函数，每次来拿对象时都要判断锁，效率低。

class Single
{
	private static Single s = null;

	private Single(){}

	public static Single getInstance()
	{
		if(s==null)
		{
			synchronized(Single.class)		
			{
				if(s==null)
		//				-->0 -->1 -->2 -->3
					s = new Single(); //这样就会产生4个对象
			}
		}
		return s;
	}
}
class  SingletonMulThreadDemo
{
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
	}
}
