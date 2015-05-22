package com.zxyairings.codelib.multithread;

/*
wait 和 sleep 区别？

1，wait可以指定时间也可以不指定。
   sleep必须指定时间。

2，在同步中时，对cpu的执行权和锁的处理不同。
	wait：释放执行权，释放锁。
	sleep:释放执行权，不释放锁。

*/

public class DiffWaitSleep {
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
	}
}

/*
 * 注意下面虽然使用了同步，但是依然有线程安全问题。
 * 
 * 需要注意的是，在同步中，即使线程拥有执行权，它也不一定能够运行。
 * 记住，在同步中，在某一时刻，只能有一个线程执行。这个线程就是持有锁的线程。
 * 在同步中，只有拥有锁的线程，并且有执行权，它才会执行。
 * 在同步中，线程想要运行，必须持有锁，如果没有锁，什么都白搭。
 */
class Try
{

	void show() throws InterruptedException
	{
		synchronized(this)// 
		{
		 
			//t0,t1,t2都进来了，但是在唤醒后，谁拥有锁，谁执行，其他没有锁的线程不会运行。
			wait();//t0 t1 t2
		
		}
	}
	void method()
	{
		synchronized(this)//t4
		{
		
			//wait();

			notifyAll();
		
		
		}//t4
	}
}