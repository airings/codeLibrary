package com.zxyairings.codelib.multithread;

/*
如果同步函数被静态修饰后，使用的锁是什么呢？

通过验证，发现不在是this。因为静态方法中也不可以定义this。

静态进内存是，内存中没有本类对象，但是一定有该类对应的字节码文件对象。
类名.class  该对象的类型是Class


静态的同步方法，使用的锁是该方法所在类的字节码文件对象。 类名.class


静态的同步函数使用的锁是  该函数所属字节码文件对象 
可以用 getClass方法获取，也可以用当前  类名.class 表示。


如何使用锁？
任何对象都可以作为锁，只要保证多个线程在同步函数，或者同步代码块中，使用的是这一个相同的对象/锁就行。
锁并不要求对象在内存中是唯一的(单例)。关键是多线程使用的是同一把锁。
所以，this对象，字节码对象getClass()，等等都可以是锁。

*/

class Ticket5 implements Runnable
{
	private static  int num = 100;
//	Object obj = new Object();
	boolean flag = true;
	public void run()
	{
//		System.out.println("this:"+this.getClass());

		if(flag)
			while(true)
			{
				synchronized(Ticket5.class)//(this.getClass())
				{
					if(num>0)
					{
						try{Thread.sleep(10);}catch (InterruptedException e){}						
						System.out.println(Thread.currentThread().getName()+".....obj...."+num--);
					}
				}
			}
		else
			while(true)
				this.show();
	}

	public static synchronized void show()
	{
		if(num>0)
		{
			try{Thread.sleep(10);}catch (InterruptedException e){}
			
			System.out.println(Thread.currentThread().getName()+".....function...."+num--);
		}
	}
}

class StaticSynFunctionLockDemo 
{
	public static void main(String[] args) 
	{
		Ticket5 t = new Ticket5();

//		Class clazz = t.getClass();
//		
//		Class clazz = Ticket.class;
//		System.out.println("t:"+t.getClass());

		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);

		t1.start();
		try{Thread.sleep(10);}catch(InterruptedException e){}
		t.flag = false;
		t2.start();
	}
}
