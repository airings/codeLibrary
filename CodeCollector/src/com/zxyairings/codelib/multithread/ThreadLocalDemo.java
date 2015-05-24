package com.zxyairings.codelib.multithread;

import java.util.Random;

/*
用于实现线程内的数据共享，即对于相同的程序代码，多个模块在同一个线程中运行时要共享一份数据，而在另外线程中运行时又共享另外一份数据。

每个线程调用全局ThreadLocal对象的set方法，就相当于往其内部的map中增加一条记录，
key分别是各自的线程，value是各自的set方法传进去的值。
在线程结束时可以调用ThreadLocal.clear()方法，
这样会更快释放内存，不调用也可以，因为线程结束后也可以自动释放相关的ThreadLocal变量。

A,B是两个模块
		 thread0 data=1 --->A--->B---> data=1
共享数据data，在不同的线程上是独立的
  		 thread1 data=2 --->A--->B---> data=1

如何实现线程范围内的共享数据的独立？
使用Thread作为key，将共享数据作为value


*/
public class ThreadLocalDemo {

	private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
	private static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();
	public static void main(String[] args) {
		for(int i=0;i<2;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() 
							+ " has put data :" + data);
					x.set(data);
/*					MyThreadScopeData myData = new MyThreadScopeData();
					myData.setName("name" + data);
					myData.setAge(data);
					myThreadScopeData.set(myData);*/
					MyThreadScopeData.getThreadInstance().setName("name" + data);
					MyThreadScopeData.getThreadInstance().setAge(data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	static class A{
		public void get(){
			int data = x.get();
			System.out.println("A from " + Thread.currentThread().getName() 
					+ " get data :" + data);
/*			MyThreadScopeData myData = myThreadScopeData.get();;
			System.out.println("A from " + Thread.currentThread().getName() 
					+ " getMyData: " + myData.getName() + "," +
					myData.getAge());*/
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("A from " + Thread.currentThread().getName() 
					+ " getMyData: " + myData.getName() + "," +
					myData.getAge());
		}
	}
	
	static class B{
		public void get(){
			int data = x.get();			
			System.out.println("B from " + Thread.currentThread().getName() 
					+ " get data :" + data);
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("B from " + Thread.currentThread().getName() 
					+ " getMyData: " + myData.getName() + "," +
					myData.getAge());			
		}		
	}
	
	class MyThreadScopeData{
		private MyThreadScopeData(){}
		public static /*synchronized*/ MyThreadScopeData getThreadInstance(){
			MyThreadScopeData instance = map.get();
			if(instance == null){
				instance = new MyThreadScopeData();
				map.set(instance);
			}
			return instance;
		}
		//private static MyThreadScopeData instance = null;//new MyThreadScopeData();
		private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();
		
		private String name;
		private int age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
	}
}
