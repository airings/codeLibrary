package com.zxyairings.codelib.multithread;

/*
其实多线程的本质是让code并发运行，这些code就是线程任务，那么只要线程任务放在Thread/Runnable的run()方法中，它就具有了并发运行的特点。

创建线程的第一种方式:继承Thread类。

创建线程的第二种方式：实现Runnable接口。

1,定义类实现Runnable接口。
2，覆盖接口中的run方法，将线程的任务代码封装到run方法中。
3，通过Thread类创建线程对象，并将Runnable接口的子类对象作为Thread类的构造函数的参数进行传递。
	为什么？因为线程的任务都封装在Runnable接口子类对象的run方法中。
	所以要让线程去指定指定对象的run方法。就必须明确该run方法所属对象。

4，调用线程对象的start方法开启线程。


实现Runnable接口的好处：
1，将线程的任务从线程的子类中分离出来，进行了单独的封装。可以理解为，runnable就是线程任务的抽象，用它来表示任务。
	按照面向对象的思想将任务的封装成对象。他的出现仅仅是将线程的任务进行了对象的封装。
2，避免了java单继承的局限性。

所以，创建线程的第二种方式较为常用。

接口就是用来扩展新功能的




在定义线程时，建立使用实现方式。

两种方式区别：
继承Thread:线程代码存放Thread子类run方法中。
实现Runnable，线程代码存在接口的子类的run方法。



*/


class Demo4 implements Runnable//extends Fu //准备扩展Demo类的功能，让其中的内容可以作为线程的任务执行。
					//通过接口的形式完成。
{
	public void run()
	{
		show();
	}
	public void show()
	{
		for(int x=0; x<20; x++)
		{
			System.out.println(Thread.currentThread().getName()+"....."+x);
		}
	}
}


class  CreateThreadwInterface
{
	public static void main(String[] args) 
	{	
		Demo4 d = new Demo4();
		Thread t1 = new Thread(d);
		Thread t2 = new Thread(d);
		t1.start();
		t2.start();


//		Demo d1 = new Demo();
//		Demo d2 = new Demo();
//		d1.start();
//		d2.start();
	}
}



/*
模拟Thread方法
class Thread 
{
	private Runnable r;
	Thread()
	{
	
	}
	Thread(Runnable r)
	{
		this.r  = r;
	}

	public void run()
	{
		if(r!=null)
			r.run();
	}

	public void start()
	{
		run();
	}
}
class ThreadImpl implements Runnable
{
	public void run()
	{
		System.out.println("runnable run");
	}
}
ThreadImpl i = new ThreadImpl();
Thread t = new Thread(i);
t.start();




class SubThread extends Thread
{
	public void run()
	{
		System.out.println("hahah");
	}
}
//SubThread s = new SubThread();
//s.start();

*/