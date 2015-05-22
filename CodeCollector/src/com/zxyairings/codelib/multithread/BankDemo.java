package com.zxyairings.codelib.multithread;

/*

需求：
银行有一个金库。
有两个储户分别存300员，每次存100，存3次。

目的：该程序是否有安全问题，如果有，如何解决？


线程安全问题产生的原因：

1，多个线程在操作共享的数据。
2，操作共享数据的线程代码有多条。

如何找问题：
1，明确哪些代码是多线程运行代码。
2，明确共享数据。- 这里是 	private Bank b = new Bank();
3，明确多线程运行代码中哪些语句是操作共享数据的。
	sum = sum + num;
	System.out.println("sum="+sum);

同步的表现形式：
	同步代码块
	同步函数

同步函数的锁见： SynFunctionLockDemo
*/

class Bank
{
	private int sum;
//	private Object obj = new Object();
	public synchronized void add(int num)//同步函数
	{
//		synchronized(obj)//同步代码块
//		{
			sum = sum + num;
	//			-->
			try{Thread.sleep(10);}catch(InterruptedException e){}
			System.out.println("sum="+sum);
//		}
	}
}


class Cus implements Runnable
{
	private Bank b = new Bank();
	public void run()
	{
		for(int x=0; x<3; x++)
		{
			b.add(100);
		}
	}
}


class BankDemo 
{
	public static void main(String[] args) 
	{
		Cus c = new Cus();
		Thread t1 = new Thread(c);
		Thread t2 = new Thread(c);
		t1.start();
		t2.start();
	}
}
