package com.zxyairings.codelib.multithread;

/*
join:
当A线程执行到了B线程的.join()方法时，A就会进入等待状态/放弃执行权。等B线程都执行完，A才会执行。

join可以用来临时加入线程执行。

线程优先级
	1 到 10
	5 是默认

Thread.currentThread().toString() prints：
    Thread[Thread-0,5,main]
5: priority
main: 线程组，ThreadGroup. 线程组有一些针对组内所有线程的方法，interrupt, setDaemon

*/

class Demo5 implements Runnable
{
	public void run()
	{
		for(int x=0; x<50; x++)
		{
			System.out.println(Thread.currentThread().toString()+"....."+x);
			Thread.yield();//释放执行权，然后自己和别人再一起来竞争cpu资源
		}
	}
}

class  JoinDemo
{
	public static void main(String[] args) throws Exception
	{
		Demo5 d = new Demo5();

		Thread t1 = new Thread(d);
		Thread t2 = new Thread(d);

		t1.start();

		t2.start();
//		t2.setPriority(Thread.MAX_PRIORITY);
		
		/*
		主线程执行到t1.join()时，main线程会释放执行权和执行资格，
		这时main线程进入冻结状态，直到t1线程结束后，才会重新进入Runnable状态才具有执行资格，
		注意main只等待t1线程，它并不管t2线程时候结束。
		这时，t1和t2都在抢夺cpu资源/执行权，等t1结束后，则是main与t2抢夺cpu资源/执行权
		哪个线程执行某个线程的join方法，哪个线程释放执行权和执行资格，然后这个某个线程会执行。
		*/
//		t1.join();//t1线程要申请加入到main线程之前，运行。临时加入一个线程运算时可以使用join方法。
		
		for(int x=0; x<50; x++)
		{
//			System.out.println(Thread.currentThread()+"....."+x);
		}
	}
}
