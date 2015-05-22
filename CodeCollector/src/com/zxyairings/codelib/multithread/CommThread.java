package com.zxyairings.codelib.multithread;

/*
线程间通讯：
其实就是多个线程在操作同一个资源，但是操作的动作不同。

买票、存钱的例子都是多个线程都在执行同一个任务，处理同一份资源，即他们都是共用同一个run方法
现在线程间的通讯则是多个线程具有不同的任务，不同的run方法，但是处理的资源还是相同的。

例子：送煤，拉煤

同一份资源，有生产者和消费者。

此代码只是示例。不严谨。

由此引出等待唤醒机制：WaitNotifyDemo.java

*/

public class CommThread {

}



//资源
class Resource
{
	String name;
	String sex;
}


//输入：input
class Input implements Runnable
{
	Resource r ;
//	Object obj = new Object();
	Input(Resource r)
	{
		this.r = r;
	}
	public void run()
	{
		int x = 0;
		while(true)
		{
			synchronized(r)
			{
				if(x==0)
				{
					r.name = "mike";
					r.sex = "man";
				}
				else
				{
					r.name = "丽丽";
					r.sex = "女女女女女女";
				}
			}
			x = (x+1)%2;

		}
	}
}
//输出
class Output implements Runnable
{

	Resource r;
//	Object obj = new Object();
	Output(Resource r)
	{
		this.r = r;
	}

	public void run()
	{
		while(true)
		{
			synchronized(r)
			{
				System.out.println(r.name+"....."+r.sex);
			}
		}
	}
}



class  ResourceDemo
{
	public static void main(String[] args) 
	{
		//创建资源。
		Resource r = new Resource();
		//创建任务。
		Input in = new Input(r);
		Output out = new Output(r);
		//创建线程，执行路径。
		Thread t1 = new Thread(in);
		Thread t2 = new Thread(out);
		//开启线程
		t1.start();
		t2.start();
	}
}
