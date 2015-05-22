package com.zxyairings.codelib.multithread;


/*
死锁：
常见情景之一：同步的嵌套。也就是同步中嵌套同步，而锁不同 

我持有锁，你持有另一个锁，我等你释放你持有的锁，而你等我释放我持有的锁

我的锁里有你的锁，你的锁里有我的锁
*/
class Ticket6 implements Runnable
{
	private  int num = 100;
	Object obj = new Object();
	boolean flag = true;
	public void run()
	{


		if(flag)
			while(true)
			{
				synchronized(obj)
				{
					show();
				}
			}
		else
			while(true)
				this.show();
	}

	public synchronized void show()
	{

		synchronized(obj)
		{
			if(num>0)
			{
				try{Thread.sleep(10);}catch (InterruptedException e){}
				
				System.out.println(Thread.currentThread().getName()+".....sale...."+num--);
			}
		}
	}
}


class DeadLockDemo 
{
	public static void main(String[] args) 
	{
		Ticket6 t = new Ticket6();
//		System.out.println("t:"+t);

		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);

		t1.start();
		try{Thread.sleep(10);}catch(InterruptedException e){}
		t.flag = false;
		t2.start();
	}
}
