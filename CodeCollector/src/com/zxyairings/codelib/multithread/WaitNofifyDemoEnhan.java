package com.zxyairings.codelib.multithread;

/*
经典实例：单生产者/单消费者。

improve WaitNotifyDemo.java

// 此实例是单生产者单消费者的情况，这个code会在多生产者多消费者时，出现问题。参见ProducerConsumerDemo.java
// 出现问题的原因是：
 * 1.线程在哪里wait，再次唤醒时，它就会从哪里继续执行。
 * 2. notify会唤醒同一锁上任意一个处于wait状态的线程，它并没有区别哪些是生产者，哪些消费者。

*/
class Resource2
{
	//私有化
	private String name;
	private String sex;
	private boolean flag = false;

	public synchronized void set(String name,String sex)
	{
		if(flag)
			try{this.wait();}catch(InterruptedException e){}
		this.name = name;
		this.sex = sex;
		flag = true;
		this.notify();
	}

	public synchronized void out()
	{
		if(!flag)
			try{this.wait();}catch(InterruptedException e){}
		System.out.println(name+"...+...."+sex);
		flag = false;
		notify();
	}
}


//输入
class Input2 implements Runnable
{
	Resource2 r ;
//	Object obj = new Object();
	Input2(Resource2 r)
	{
		this.r = r;
	}
	public void run()
	{
		int x = 0;
		while(true)
		{
			if(x==0)
			{
				r.set("mike","nan");
			}
			else
			{
				r.set("丽丽","女女女女女女");
			}
			x = (x+1)%2;
		}
	}
}
//输出
class Output2 implements Runnable
{

	Resource2 r;
//	Object obj = new Object();
	Output2(Resource2 r)
	{
		this.r = r;
	}

	public void run()
	{
		while(true)
		{
			r.out();
		}
	}
}



class  WaitNofifyDemoEnhan
{
	public static void main(String[] args) 
	{
		//创建资源。
		Resource2 r = new Resource2();
		//创建任务。
		Input2 in = new Input2(r);
		Output2 out = new Output2(r);
		//创建线程，执行路径。
		Thread t1 = new Thread(in);
		Thread t2 = new Thread(out);
		//开启线程
		t1.start();
		t2.start();
	}
}
