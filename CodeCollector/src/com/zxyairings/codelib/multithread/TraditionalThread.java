package com.zxyairings.codelib.multithread;

/*
线程的编写方式：
	继承trhead类
	实现Runnable接口，Runnable就是任务
使用内部类来简化代码。
*/
public class TraditionalThread {
	public static void main(String[] args) {
		
		//这里使用匿名类，表示的是新建一个Thread的子类的对象，而不是Thread的对象
		Thread thread = new Thread(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("1:" + Thread.currentThread().getName());
					System.out.println("2:" + this.getName());//this 代表这个thread类的子类对象
				}
			}
		};
		thread.start();
		
		//这种写法更可以体现面向对象的思想，将线程对象和线程需要运行的任务对象分离开，通过组合相互作用。
		Thread thread2 = new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("1:" + Thread.currentThread().getName());
//					System.out.println("2:" + this.getName()); 这时这就不能用this了，这个this代表的是Runnable的实现类的对象，他不是线程，所以这里就不能使用了。
				}				
				
			}
		});
		thread2.start();
		
		//运行的是thread子类中的run方法，原理就是在Thread类的run方法中。
		//思考：匿名内部类对象的构造方法如何调用父类的非默认构造方法。
		new Thread(
				new Runnable(){
					public void run() {
						while(true){
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("runnable :" + Thread.currentThread().getName());

						}							
					}
				}
		){
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread :" + Thread.currentThread().getName());

				}	
			}
		}.start();
		
		
	}
}
