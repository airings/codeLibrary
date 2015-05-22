package com.zxyairings.codelib.multithread;

import java.util.concurrent.locks.*;

/*
Object obj = new Object();
void show()
{
	synchronized(obj) 同步代码块/同步函数, 对于锁的操作是隐式的。
	{
		code...
	}
}

Lock lock = new ReentrantLock();    jdk1.5以后将同步和锁封装成了对象。并将操作锁的隐式方式定义到了该对象中，将隐式动作变成了显示动作。
void show()
{
	lock.lock(); 获取锁
	try
	{
		// access the resource protected by this lock
		code...
	} finally
	{
		lock.unlock(); 释放锁，一定放在finally中
	}
}


ReentrantLock: 一个可重入的互斥锁 Lock，它具有与使用 synchronized 方法和语句所访问的隐式监视器锁相同的一些基本行为和语义，但功能更强大。

JDK1.5 中提供了多线程升级解决方案。
将同步Synchronized替换成现实Lock操作。
	Lock实现提供了比使用synchronized函数和代码块可获得的更广泛的锁定操作。
	锁是控制多个线程对共享资源进行访问的工具。通常锁提供了对共享资源的独占访问。一次只能有一个线程获得锁，对共享资源的所有访问都需要首先获得锁。
	synchronized 方法或语句的使用提供了对与每个对象相关的隐式监视器锁的访问，但却强制所有锁获取和释放均要出现在一个块结构中：当获取了多个锁时，它们必须以相反的顺序释放，且必须在与所有锁被获取时相同的词法范围内释放所有锁。
	虽然 synchronized 方法和语句的范围机制使得使用监视器锁编程方便了很多，而且还帮助避免了很多涉及到锁的常见编程错误，但有时也需要以更为灵活的方式使用锁。
	例如，某些遍历并发访问的数据结果的算法要求使用 "hand-over-hand" 或 "chain locking"：获取节点 A 的锁，然后再获取节点 B 的锁，然后释放 A 并获取 C，然后释放 B 并获取 D，依此类推。
	Lock 接口的实现允许锁在不同的作用范围内获取和释放，并允许以任何顺序获取和释放多个锁，从而支持使用这种技术。

Condition 将 Object 监视器方法（wait、notify 和 notifyAll）分解成截然不同的对象，
以便通过将这些对象与任意 Lock 实现组合使用，为每个对象提供多个等待 set（wait-set）。其中，Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用。
	Conditions (also known as condition queues or condition variables) provide a means for one thread to suspend execution (to "wait") until notified by another thread that some state condition may now be true. 
	Because access to this shared state information occurs in different threads, it must be protected, so a lock of some form is associated with the condition. 
	The key property that waiting for a condition provides is that it atomically releases the associated lock and suspends the current thread, just like Object.wait.

	A Condition instance is intrinsically bound to a lock. To obtain a Condition instance for a particular Lock instance use its newCondition() method.

summary：
	1.lock还是原来锁的概念，只不过之前锁的概念=监视器的概念。监视器是一个具有改变线程对象方法的对象(即具有wait/notify/notifyAll方法的对象)，因为这些对象定义在Object中，而任何Object都可以是锁，所以锁也就是监视器--锁
	承担了监视器的功能。这样一个锁上只能有一个监视器。这个监视器既监视生产者又监视消费者，所以他的notifyAll无法区别生产者和消费者。
	2.但是在java1.5以后，将监视器与锁分离开了，锁就变成了Lock，而监视器就抽象成了Condition对象(Condition factors out the Object monitor methods (wait, notify and notifyAll) into distinct objec
	这样Condition对象就可以跟任意 Lock 实现组合使用。Condition 实例实质上被绑定到一个锁上。一个锁上可以绑定多个Condition对象，即监视器对象。也就是一个锁上可以有多个监视器。这样我们可以让一个监视器监视生产者，
	一个监视器监视消费者，这样消费者的监视器只会唤醒消费者线程，生产者也一样。这样就做到了区分。
	3.监视器是如何做到分组监控的？它的原理只不过是，哪个监视器await哪些线程，那么这个监视器只能signal哪些线程。通过这种方式实现分组监控。



将Object中的wait，notify notifyAll，替换了Condition对象。
该对象可以Lock锁 进行获取。
该示例中，实现了本方只唤醒对方操作。

Lock:替代了Synchronized
	lock 
	unlock
	newCondition()

Condition：替代了Object wait notify notifyAll
	await();
	signal();
	signalAll();
*/


/*
jdk1.5以后将同步和锁封装成了对象。 
并将操作锁的隐式方式定义到了该对象中，
将隐式动作变成了显示动作。

Lock接口： 出现替代了同步代码块或者同步函数。将同步的隐式锁操作变成显示锁操作。
同时更为灵活。可以一个锁上加上多组监视器(即Condition对象)。
lock():获取锁。
unlock():释放锁，通常需要定义finally代码块中。


Condition接口：出现替代了Object中的wait notify notifyAll方法。
			将这些监视器方法单独进行了封装，变成Condition监视器对象。
			可以任意锁进行组合。
await(); -- wait
signal(); -- notify
signalAll(); -- notifyAll


注意：当需要判断标记时，一定要用while，因为这样安全，线程每一次醒来都会来判断这个标记，这样就不会出现像使用if时那样的错误。

java1.5解决多生产者多消费者的原理是：一个锁上挂着多个监视器。
*/

class Resource4
{
	private String name;
	private int count = 1;
	private boolean flag = false;

//	创建一个锁对象。
	Lock lock = new ReentrantLock();

	//通过已有的锁获取该锁上的监视器对象。
//	Condition con = lock.newCondition();

	//通过已有的锁获取两个监视器，一个监视生产者，一个监视消费者。
	Condition producer_con = lock.newCondition();
	Condition consumer_con = lock.newCondition();

	
	public  void set(String name)//  t0 t1
	{
		lock.lock();
		try
		{
			while(flag)
//			try{lock.wait();}catch(InterruptedException e){}//   t1    t0
			try{producer_con.await();}catch(InterruptedException e){}//   t1    t0
		
			this.name = name + count;//烤鸭1  烤鸭2  烤鸭3
			count++;//2 3 4
			System.out.println(Thread.currentThread().getName()+"...生产者5.0..."+this.name);//生产烤鸭1 生产烤鸭2 生产烤鸭3
			flag = true;
//			notifyAll();
//			con.signalAll();
			consumer_con.signal();
		}
		finally
		{
			lock.unlock();
		}
		
	}

	public  void out()// t2 t3
	{
		lock.lock();
		try
		{
			while(!flag)
//			try{this.wait();}catch(InterruptedException e){}	//t2  t3
			try{consumer_con.await();}catch(InterruptedException e){}	//t2  t3
			System.out.println(Thread.currentThread().getName()+"...消费者.5.0......."+this.name);//消费烤鸭1
			flag = false;
//			notifyAll();
//			con.signalAll();
			producer_con.signal();
		}
		finally
		{
			lock.unlock();
		}
		
	}
}

class Producer2 implements Runnable
{
	private Resource4 r;
	Producer2(Resource4 r)
	{
		this.r = r;
	}
	public void run()
	{
		while(true)
		{
			r.set("烤鸭");
		}
	}
}

class Consumer2 implements Runnable
{
	private Resource4 r;
	Consumer2(Resource4 r)
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



class  ProducerConsumerDemo_java5
{
	public static void main(String[] args) 
	{
		Resource4 r = new Resource4();
		Producer2 pro = new Producer2(r);
		Consumer2 con = new Consumer2(r);

		Thread t0 = new Thread(pro);
		Thread t1 = new Thread(pro);
		Thread t2 = new Thread(con);
		Thread t3 = new Thread(con);
		t0.start();
		t1.start();
		t2.start();
		t3.start();

	}
}

