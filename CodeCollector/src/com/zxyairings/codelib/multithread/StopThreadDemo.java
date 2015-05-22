package com.zxyairings.codelib.multithread;

/*

停止线程：
1，stop方法:stop方法已经过时。

2，run方法结束。
	如何停止线程？只有一种，run方法结束。

开启多线程运行，运行代码通常是循环结构。只要控制住循环，就可以让run方法结束，也就是线程结束。

怎么控制线程的任务结束呢?
一般在run方法中都有循环，也就是在任务中都会有循环结构，只要控制住循环就可以结束任务。

控制循环通常就用定义标记来完成。这种方式很常用，但是也有一个特殊情况它无法使线程停止下来。

这特殊情况就是：
当线程处于了冻结状态。就不会读取到标记。那么线程就不会结束。

但是如果线程处于了冻结状态，无法读取标记。如何结束呢？
当没有指定的方式让冻结的线程恢复到运行状态时，这时需要对冻结进行清除。
强制让线程恢复到运行状态中来。这样就可以操作标记让线程结束。

可以使用Thread类提供的interrupt()方法，将线程从冻结状态强制恢复到运行状态中来，让线程具备cpu的执行资格。 

当时强制动作会发生了InterruptedException，记得要处理

*/
class StopThread implements Runnable
{
	private boolean flag = true;//线程结束标记
	public synchronized void run()
	{
		while(flag)
		{
			try
			{
				wait();//t0 t1
			}
			catch (InterruptedException e)
			{
				System.out.println(Thread.currentThread().getName()+"....."+e);
				flag = false;
			}
			
			System.out.println(Thread.currentThread().getName()+"......++++");
		}
	}
	public void setFlag()
	{
		flag = false;
	}
}



class StopThreadDemo 
{
	public static void main(String[] args) 
	{
		StopThread st = new StopThread();

		Thread t1 = new Thread(st);
		Thread t2 = new Thread(st);
		// 用户线程/守护线程/后台线程 与前台线程/一般线程 在开启、运行都没有区别
		// 只有结束有区别：当所有的前台线程结束后，Java虚拟机退出，后台线程会自动结束，不用刻意结束
		// 有点后台依赖前台的意思
		t1.start();
		t2.setDaemon(true);
		t2.start();


		int num = 1;
		for(;;)
		{
			if(++num==50)
			{
//				st.setFlag();//设置标记，使线程结束循环
				t1.interrupt();
//				t2.interrupt();
				break;
			}
			System.out.println("main...."+num);
		}

		System.out.println("over");
	}
}
