package com.zxyairings.codelib.multithread;

//线程的同步互斥

public class TraditionalThreadSynchronized {


	
	public static void main(String[] args) {
		//在静态方法中不能new内部类的实例对象，因为内部类与外部类对象挂钩，必须先有外部类对象；因为内部类可以访问外部类的成员变量，但是在静态方法中这时还没有外部类的对象。
		new TraditionalThreadSynchronized().init();
	}
	
	private void init(){
		final Outputer outputer = new Outputer();//内部类不能访问局部变量，如果要访问需要加final
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output("zhangxiaoxiang");
				}
				
			}
		}).start();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output3("lihuoming");
				}
				
			}
		}).start();
		
	}

	static class Outputer{
		
		public void output(String name){
			int len = name.length();
			synchronized (Outputer.class) //互斥一定要是这个对象是同一个对象。跟output2互斥要用this
			{
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
//		锁是this
		public synchronized void output2(String name){
			int len = name.length();
			for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
			}
			System.out.println();
		}
//		锁是Outputer.class
		public static synchronized void output3(String name){
			int len = name.length();
			for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
			}
			System.out.println();
		}	
	}
}
