package com.zxyairings.codelib.multithread;
/*
*
*面试题： 
*子线程循环10次，接着主线程循环100次，接着又回到子线程循环10次，
*接着又回到主线程又循环100次，如此循环50次。
*
*下面code还没有完全实现需求，但是这么写不好，这里先对其进行改写：
*这里介绍一个重要的面向对象的思想：
*	把有关联的方法集中到一个类中，(什么叫有关联?)
*	或者将操作共享数据的方法集中在同一个类中，
*	或者将使用同样算法的方法集中在同一个类中，
*	或者将需要同步的方法集中在同一个类中，
*	...
*	这就是高内聚。
*
*对下面的写法进行改造，TraditionalThreadCommunication2.java
*/
public class TraditionalThreadCommunication {

	public static void main(String[] args) {
		
//		子线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					synchronized (TraditionalThreadCommunication.class) {//这里使用字节码锁，不太好，因为字节码只有一套，除非这个程序要求所有的线程都互斥，那么可以使用字节码当锁
						for (int j = 0; j < 10; j++) {
							System.out.println("sub thread sequence of " + j + ",loop of " + i);
						}
					}
				}
			}
		}).start();
		
		//main线程
		for (int i = 0; i < 50; i++) {
			synchronized (TraditionalThreadCommunication.class) {
				for (int j = 0; j < 100; j++) {
					System.out.println("main thread sequence of " + j + ",loop of " + i);
				}
			}
		}
	}
}
