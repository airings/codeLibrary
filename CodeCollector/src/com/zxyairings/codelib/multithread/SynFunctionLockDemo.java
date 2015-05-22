package com.zxyairings.codelib.multithread;

/*
同步函数用的是哪一个锁呢？
函数需要被对象调用。那么函数都有一个所属对象引用。就是this。
所以同步函数使用的锁是this。


同步函数和同步代码块的区别：
同步函数的锁是固定的this。

同步代码块的锁是任意的对象。

建议使用同步代码块。


如何使用锁？
任何对象都可以作为锁，只要保证多个线程在同步函数，或者同步代码块中，使用的是这一个相同的对象/锁就行。
锁并不要求对象在内存中是唯一的(单例)。关键是多线程使用的是同一把锁。
所以，this对象，字节码对象getClass()，等等都可以是锁。

*/
class Ticket4 implements Runnable
{
	private  int num = 100;
//	Object obj = new Object();
	boolean flag = true;
	//如果在run方法上加同步的话，那么只有一个线程可以进到run中，即使在这个线程sleep后，
	//其他线程虽然分配到CPU时间，但是他们进不到run中，因为之前的线程没有释放锁。
	public void run()
	{
//		System.out.println("this:"+this);

		if(flag)//真：同步代码块 假：同步函数
			while(true)
			{
				synchronized(this)
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

	public synchronized void show()
	{
		if(num>0)
		{
			try{Thread.sleep(10);}catch (InterruptedException e){}
			
			System.out.println(Thread.currentThread().getName()+".....function...."+num--);
		}
	}
}

class SynFunctionLockDemo 
{
	public static void main(String[] args) 
	{
		Ticket4 t = new Ticket4();
//		System.out.println("t:"+t);

		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);

		t1.start();//同步代码块
		try{Thread.sleep(10);}catch(InterruptedException e){}
		t.flag = false;//这句有线程安全问题，因为有共享数据
		t2.start();//同步函数
	}
}

