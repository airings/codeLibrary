package com.zxyairings.codelib.multithread;
/*
*
*面试题： 
*子线程循环10次，接着主线程循环100次，接着又回到子线程循环10次，
*接着又回到主线程又循环100次，如此循环50次。
*
*
*这里介绍一个重要的面向对象的思想：
*	把有关联的方法集中到一个类中，(什么叫有关联?)
*	或者将操作共享数据的方法集中在同一个类中，
*	或者将使用同样算法的方法集中在同一个类中，
*	或者将需要同步的方法集中在同一个类中，
*	...
*	这就是高内聚。
*
*按照该思想，改写后。
*
*经验：要用到共同数据（包括同步锁）或共同算法的若干方法应该归在同一个类身上，这种设计正好体现了高类聚和程序的健壮性。
*/
public class TraditionalThreadCommunication2 {
	

	public static void main(String[] args) {
		
		final Business business = new Business();
		
//		子线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					business.sub(i);
				}
			}
		}).start();
		
		//main线程
		for (int i = 0; i < 50; i++) {
			business.main(i);
		}
	}
}

class Business{
	public void sub(int i){
		for (int j = 0; j < 10; j++) {
			System.out.println("sub thread sequence of " + j + ",loop of " + i);
		}
	}
	
	public void main(int i){
		for (int j = 0; j < 100; j++) {
			System.out.println("main thread sequence of " + j + ",loop of " + i);
		}
	}
}
