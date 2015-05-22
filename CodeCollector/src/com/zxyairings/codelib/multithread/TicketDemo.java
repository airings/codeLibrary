package com.zxyairings.codelib.multithread;


/*
需求：简单的卖票程序。
多个窗口同时买票。

通过分析，发现，打印出0，-1，-2等错票。

多线程的运行出现了安全问题。

问题的原因：
	当多条语句在操作同一个线程共享数据时，一个线程对多条语句只执行了一部分，还没有执行完，
	另一个线程参与进来执行。导致共享数据的错误。

解决办法：
	对多条操作共享数据的语句，只能让一个线程都执行完。在执行过程中，其他线程不可以参与执行。


线程安全问题产生的原因：

1，多个线程在操作共享的数据。
2，操作共享数据的线程代码有多条。

当一个线程在执行操作共享数据的多条代码过程中，其他线程参与了运算。
就会导致线程安全问题的产生。 


解决思路；火车上的卫生间
就是将多条操作共享数据的线程代码封装起来，当有线程在执行这些代码的时候，
其他线程时不可以参与运算的。
必须要当前线程把这些代码都执行完毕后，其他线程才可以参与运算。 

在java中，用同步代码块就可以解决这个问题。

同步代码块的格式：
synchronized(对象)
{
	需要被同步的代码 ；
}
这里的对象是：锁，可以理解为标志位。为什么要是个对象？因为后期要对同步中的线程进行监视操作，监视的方法都在锁上。
这里的对象如同锁。持有锁的线程可以在同步中执行。
没有持有锁的线程即使获取cpu的执行权，也进不去，因为没有获取锁。

如何使用锁？
任何对象都可以作为锁，只要保证多个线程在同步函数，或者同步代码块中，使用的是这一个相同的对象/锁就行。
锁并不要求对象在内存中是唯一的(单例)。关键是多线程使用的是同一把锁。
所以，this对象，字节码对象getClass()，等等都可以是锁。

同步的好处：解决了线程的安全问题。


同步的弊端：相对降低了效率，因为同步外的线程的都会判断同步锁，较为消耗资源，


同步的前提：同步中必须有多个线程并使用同一个锁。
1，必须要有两个或者两个以上的线程。
2，必须是多个线程使用同一个锁。
未满足这两个条件，不能称其为同步。
必须保证同步中只能有一个线程在运行。

同步出现问题的话，可以先从同步的前提入手。

*/

class Ticket implements Runnable//extends Thread
{
	private  int num = 100;
	Object obj = new Object();//锁
	
	public void run()
	{
//		Object obj = new Object();//如果锁放这里，那么每个线程都有一把锁，那么他们就不同步了。
		while(true)
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
}


class  TicketDemo
{
	public static void main(String[] args) 
	{

		Ticket t = new Ticket();//创建一个线程任务对象。

		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		Thread t3 = new Thread(t);
		Thread t4 = new Thread(t);

		t1.start();
		t2.start();
		t3.start();
		t4.start();	
	}
}



//下面的程序有个问题：4个窗口会卖400张票，但是票总共就100张。
//原因是线程的num都是独立的。
//一种解决方法是将num变成static，以实现数据在线程间的共享。

/*
想通过一个线程，开启4次的方式是不行的！
Ticket t1 = new Ticket();
t1.start();
t1.start();//一个线程不能开启两次，会抛出无效线程状态异常，主线程在此发生异常退出，但是t1会继续运行
t1.start();
t1.start();
*/
class Ticket2 extends Thread
{
	private int num = 100;
//	private static int num = 100; //one solution
	public void run()
	{
		while(true)
		{
			if(num>0)
			{
				System.out.println(Thread.currentThread().getName()+".....sale...."+num--);
			}
			
		}
	}
}

class  TicketDemo2
{
	public static void main(String[] args) 
	{
		Ticket2 t1 = new Ticket2();
		Ticket2 t2 = new Ticket2();
		Ticket2 t3 = new Ticket2();
		Ticket2 t4 = new Ticket2();
		t1.start();
		t2.start();
		t3.start();
		t4.start();		
	}
}


//更好的解决方式是通过runnable
//这样虽然可以创建4个线程买票，但是线程任务只有一个对象就是Ticke3，所以num只有一个，所以也可以实现4个线程共同卖100张票
//但是这里有线程安全问题：因为4个线程会同时操作num这个共享数据。
class Ticket3 implements Runnable
{
	private int num = 100;
	public void run()
	{
		while(true)
		{
			if(num>0)
			{
				System.out.println(Thread.currentThread().getName()+".....sale...."+num--);
			}
			
		}
	}
}

class  TicketDemo3
{
	public static void main(String[] args) 
	{
		Ticket t = new Ticket();//创建一个线程任务对象。
//		Ticket tt = new Ticket();//也可以创建多个线程任务
		
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		Thread t3 = new Thread(t);
		Thread t4 = new Thread(t);
//		Thread t3 = new Thread(tt);
//		Thread t4 = new Thread(tt);

		t1.start();
		t2.start();
		t3.start();
		t4.start();		
	}
}

