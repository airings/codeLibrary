package com.zxyairings.codelib.multithread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
用于实现线程内的数据共享，即对于相同的程序代码，多个模块在同一个线程中运行时要共享一份数据，而在另外线程中运行时又共享另外一份数据。


A,B是两个模块
		 thread0 data=1 --->A--->B---> data=1
共享数据data，在不同的线程上是独立的
  		 thread1 data=2 --->A--->B---> data=1

如何实现线程范围内的共享数据的独立？
使用Thread作为key，将共享数据作为value

java提供了这样的类来完成线程内的共享数据，它就相当于这里的map threadData. 见ThreadLocalDemo.java
*/
public class ThreadScopeShareData {
	private static int data = 0;
	private static Map<Thread, Integer> threadData = new HashMap<Thread, Integer>();
	public static void main(String[] args) {
		for(int i=0;i<2;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() 
							+ " has put data :" + data);
					threadData.put(Thread.currentThread(), data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	static class A{
		public void get(){
			int data = threadData.get(Thread.currentThread());
			System.out.println("A from " + Thread.currentThread().getName() 
					+ " get data :" + data);
		}
	}
	
	static class B{
		public void get(){
			int data = threadData.get(Thread.currentThread());			
			System.out.println("B from " + Thread.currentThread().getName() 
					+ " get data :" + data);
		}		
	}
}
