package com.zxyairings.codelib.multithread;

/*
等待/唤醒机制。 

涉及的方法：

1，wait(): 让线程处于冻结状态，被wait的线程会被存储到线程池中。
2，notify():唤醒线程池中一个线程(任意).
3，notifyAll():唤醒线程池中的所有线程。
	这些方法都必须定义在同步中。这些方法是锁的方法，不是线程的方法，而且必须隶属于同一个锁。而锁必须在同步中，所以这些方法必须在同步中。
	因为这些方法是用于操作线程状态的方法，其实是监视线程状态的并改变线程状态，如果线程想要自己的状态发生改变，那么就必须明确一个锁来改变的自己的状态。也就是我们必须要明确到底操作的是哪个锁上的线程。
	锁的特点是对多个线程进行同步，不同的同步对应不同的锁，那么线程要wait就必须说明它是在哪个锁上wait，然后锁会调用wait方法改变该线程的状态。notify也一样。
	
	例如：线程1在A锁上wait，线程2可以在B锁上notify线程1么？肯定是不行的。
	举例：两组小朋友在玩木头人游戏，各自玩各自的，一组中的小朋友wait了，另一组的小朋友是不能来救的。

都使用在同步中，因为要对持有监视器(锁)的线程操作。
所以他们要使用在同步块/同步方法中，因为只有同步才具有锁。

为什么操作线程的方法wait notify notifyAll定义在了Object类中？ 
	因为这些方法在操作同步中线程时，都必须要标识它们所操作线程只有的锁，只有同一个锁上的被等待线程，可以被同一个锁上notify唤醒。
	不可以对不同锁中的线程进行唤醒。也就是说，等待和唤醒必须是同一个锁。而锁可以是任意对象，所以可以被任意对象调用的方法定义Object类中。
	因为这些方法是监视器的方法。监视器其实就是锁。锁可以是任意的对象，任意的对象调用的方式一定定义在Object类中。


思考1：wait(),notify(),notifyAll(),用来操作线程为什么定义在了Object类中？
1，这些方法存在与同步中。
2，使用这些方法时必须要标识所属的同步的锁。
3，锁可以是任意对象，所以任意对象调用的方法一定定义Object类中。

思考2：wait(),sleep()有什么区别？
wait():释放cpu执行权，释放锁。
sleep():释放cpu执行权，不释放锁。



*/
//资源
class Resource1
{
	String name;
	String sex;
	boolean flag = false;
}


//输入
class Input1 implements Runnable
{
	Resource1 r ;
//	Object obj = new Object();
	Input1(Resource1 r)
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
				if(r.flag)
					try{r.wait();/*r锁的wait方法改变current thread的状态*/}catch(InterruptedException e){}
				if(x==0)
				{
					r.name = "mike";
					r.sex = "nan";
				}
				else
				{
					r.name = "丽丽";
					r.sex = "女女女女女女";
				}
				r.flag = true;
				r.notify();//r锁的notify方法唤醒在r锁上wait的线程
			}
			x = (x+1)%2;

		}
	}
}
//输出
class Output1 implements Runnable
{

	Resource1 r;
//	Object obj = new Object();
	Output1(Resource1 r)
	{
		this.r = r;
	}

	public void run()
	{
		while(true)
		{
			synchronized(r)
			{
				if(!r.flag)
					try{r.wait();}catch(InterruptedException e){}
				System.out.println(r.name+"....."+r.sex);
				r.flag = false;
				r.notify();
			}
		}
	}
}



class  WaitNotifyDemo
{
	public static void main(String[] args) 
	{
		//创建资源。
		Resource1 r = new Resource1();
		//创建任务。
		Input1 in = new Input1(r);
		Output1 out = new Output1(r);
		//创建线程，执行路径。
		Thread t1 = new Thread(in);
		Thread t2 = new Thread(out);
		//开启线程
		t1.start();
		t2.start();
	}
}
