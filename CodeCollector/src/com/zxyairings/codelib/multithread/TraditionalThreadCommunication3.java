
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
*
*增加线程间的通信，这样就完成了题目要求。
*
*再次强调一下：
*	锁的代码和互斥的代码和通信的代码而不是放在线程的代码中，而是放在资源类的内部方法中。线程的run方法只是调用资源类的方法来运行而已。
*	互斥的代码，通信的代码不是写在线程上，而是要写在线程索要访问的资源的内部的。好处是，以后这个资源类交给任何一个线程运行，他们就
*	显然同步了，不同再次考虑线程同步的问题了。
*
*数据在哪个类，那么操作这个数据的方法就在哪个类。这就是专家模式。
*
*/
public class TraditionalThreadCommunication3 {
	
	public static void main(String[] args) {
		
		final Business1 business = new Business1();
		
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

class Business1{
	private boolean bShouldSub = true;
	public void sub(int i){
		while (!bShouldSub) { //这里while比用if好，更健壮，防止假唤醒
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 0; j < 10; j++) {
			System.out.println("sub thread sequence of " + j + ",loop of " + i);
		}
		bShouldSub=false;
		this.notify();
	}
	
	public void main(int i){
		while (bShouldSub) {//这里while比用if好，更健壮，防止假唤醒
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 0; j < 100; j++) {
			System.out.println("main thread sequence of " + j + ",loop of " + i);
		}
		bShouldSub = true;
		this.notify();
	}
}
