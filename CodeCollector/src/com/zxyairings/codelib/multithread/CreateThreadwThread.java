package com.zxyairings.codelib.multithread;


/*
如何创建一个线程呢？

创建线程方式一：继承Thread类。

步骤：
1，定义一个类继承Thread类。
2，覆盖Thread类中的run方法。
3，直接创建Thread的子类对象创建线程。
4，调用start方法开启线程并调用线程的任务run方法执行。



为什么要覆盖run方法呢？

Thread类用于描述线程。
该类就定义了一个功能，用于存储线程要运行的代码。该存储功能就是run方法。

也就是说Thread类中的run方法，用于存储线程要运行的代码。


发现运行结果每一次都不同。
因为多个线程都获取cpu的执行权。cpu执行到谁，谁就运行。
明确一点，在某一个时刻，只能有一个程序在运行。(多核除外)
cpu在做着快速的切换，以达到看上去是同时运行的效果。
我们可以形象把多线程的运行行为在互相抢夺cpu的执行权。

这就是多线程的一个特性：随机性。谁抢到谁执行，至于执行多长，cpu说的算。


如何区分不同的线程？
可以通过Thread的getName获取线程的名称 Thread-编号(从0开始)

主线程的名字就是main。



局限性：
除了Thread类，不能继承其他的类

接口就是用来扩展新功能的

*/

class Demo1 extends Thread
{
	private String name;
	Demo1(String name)
	{
		super(name);
		//this.name = name;
	}
	public void run()
	{
		for(int x=0; x<10; x++)
		{
			//for(int y=-9999999; y<999999999; y++){}
			System.out.println(name+"....x="+x+".....name="+Thread.currentThread().getName());
		}
	}
}




class CreateThreadwThread 
{
	public static void main(String[] args) 
	{

		/*
		创建线程的目的是为了开启一条执行路径，去运行指定的代码和其他代码实现同时运行。
		
		而运行的指定代码就是这个执行路径的任务。

		jvm创建的主线程的任务都定义在了主函数中。

		而自定义的线程它的任务在哪儿呢？
		Thread类用于描述线程，线程是需要任务的。所以Thread类也对任务的描述。
		这个任务就通过Thread类中的run方法来体现。也就是说，run方法就是封装自定义线程运行任务的函数。
		
		run方法中定义就是线程要运行的任务代码。

		开启线程是为了运行指定代码，所以只有继承Thread类，并复写run方法。
		将运行的代码定义在run方法中即可。 


		*/

		//我们想让d1,d2同时运行，那么只需要让他们在不同的执行路径当中就可
		Demo1 d1 = new Demo1("旺财");
		Demo1 d2 = new Demo1("xiaoqiang");
//		d1.run();
//		d2.run();
		d1.start();//开启线程，调用run方法。	
		d2.start();
		System.out.println("over...."+Thread.currentThread().getName());
	}
}
//调用run和调用start有什么区别？
/*调用run方法还是普通的对象方法，主线程会顺序执行，目前只有一个线程就是主线程，所以在d1/d2中的run方法中的Thread.currentThread().getName()="main"
 *调用start方法，会开启线程，并调用run方法，这才是真正的运行线程。
 * 
 * 
 */
 