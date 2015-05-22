package com.zxyairings.codelib.multithread;

/*
多线程的运行内存图
每个线程会创建一个独立的栈空间，都有各自的局部变量x，一个线程中产生的异常不影响其他的线程：主线程中产生的异常，不印象其他线程，也不会引起jvm的停止。
jvm会在所有的线程结束后才停止
 * 
 */

class Demo3 extends Thread
{
	private String name;
	Demo3(String name)
	{
//		super(name);
		this.name = name;
	}
	public void run()
	{
		int[] arr = new int[3];
		System.out.println(arr[3]);
		for(int x=0; x<10; x++)
		{
			System.out.println("....x="+x+".....name="+Thread.currentThread().getName());
		}
	}
}




class ThreadDemo3 
{
	public static void main(String[] args) 
	{
		Demo3 d1 = new Demo3("旺财");
		Demo3 d2 = new Demo3("xiaoqiang");
		d1.start();
		
		d2.start();

		System.out.println(4/0);//throw new ArithmeticException();

		for(int x=0; x<20; x++)
		{
			System.out.println(x+"...."+Thread.currentThread().getName());
		}
	}
}
