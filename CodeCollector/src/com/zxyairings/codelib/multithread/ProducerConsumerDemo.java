package com.zxyairings.codelib.multithread;


/*
经典实例：多生产者/多消费者。

多生产者，多消费者的问题。
出现问题的原因是：
1.线程在哪里wait，再次唤醒时，它就会从哪里继续执行。
	所以，再次唤醒时并不判断标记(if(flag)的情况)。改成while(flag)就可以解决这个问题：每次新唤醒的标记都会重新判断标记。
	但是这样会产生所有线程都wait的新问题。产生这个新问题的原因是: (例如)生产者没有唤醒消费者，而是唤醒了另一个生产者，进而它也wait，所以所有线程全部wait。
	关键原因是一方没有唤醒另一方的线程而唤醒了本方的线程。由于java没有提供唤醒特定一个线程的方法，所以解决这个问题的方法就是全部唤醒，改notify为notifyAll。
2. notify会唤醒同一锁上任意一个处于wait状态的线程，它并没有区别哪些是生产者，哪些消费者。
	这个在java1.5之后得到了解决，而且比上面的while+notifyAll的解决方法更高效。采用Lock+Conditions的解决方法：ProducerConsumerDemo_java5.java

if判断标记，只有一次，会导致不该运行的线程运行了。出现了数据错误的情况。
while判断标记，解决了线程获取执行权后，是否要运行！

notify:只能唤醒一个线程，如果本方唤醒了本方，没有意义。而且while判断标记+notify会导致死锁(4个线程全部wait)。
notifyAll解决了本方线程一定会唤醒对方线程的问题。

对于多个生产者和消费者。
为什么要定义while判断标记。
原因：让被唤醒的线程再一次判断标记。


为什么定义notifyAll，
因为需要唤醒对方线程。
因为只用notify，容易出现只唤醒本方线程的情况。导致程序中的所有线程都等待。

所以以前开发时只要是多生产多消费的时候，用得全都是 while判断标记+notifyAll。
从这个解决方法可以看出，这个while+notifyAll的解决方案的效率不高，因为notifyAll会唤醒本方线程，还要进行没有意义的标记判断，这降低了效率。

能不能只唤醒对方呢？
所以在jdk1.5提供了新的解决方案，见 ProducerConsumerDemo_java5.java

*/

class Resource3
{
	private String name;
	private int count = 1;
	private boolean flag = false;//判断资源是否生产/消费的标记
	public synchronized void set(String name)//  
	{
		while(flag)//if(flag)
			try{this.wait();}catch(InterruptedException e){}//   t1    t0
		
		this.name = name + count;//烤鸭1  烤鸭2  烤鸭3
		count++;//2 3 4
		System.out.println(Thread.currentThread().getName()+"...生产者..."+this.name);//生产烤鸭1 生产烤鸭2 生产烤鸭3
		flag = true;
		notifyAll();
	}

	public synchronized void out()//  t3
	{
		while(!flag)//if(!flag)
			try{this.wait();}catch(InterruptedException e){}	//t2  t3
		System.out.println(Thread.currentThread().getName()+"...消费者........"+this.name);//消费烤鸭1
		flag = false;
		notifyAll();
	}
}

class Producer implements Runnable
{
	private Resource3 r;
	Producer(Resource3 r)
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

class Consumer implements Runnable
{
	private Resource3 r;
	Consumer(Resource3 r)
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



class  ProducerConsumerDemo
{
	public static void main(String[] args) 
	{
		Resource3 r = new Resource3();
		Producer pro = new Producer(r);
		Consumer con = new Consumer(r);

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

