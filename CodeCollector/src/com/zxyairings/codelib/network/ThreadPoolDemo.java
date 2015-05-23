package com.zxyairings.codelib.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
	public static void main(String[] args) {
		//ExecutorService threadPool = Executors.newFixedThreadPool(3); 固定的线程池
		//ExecutorService threadPool = Executors.newCachedThreadPool(); 缓存的线程池，线程数目不固定
		ExecutorService threadPool = Executors.newSingleThreadExecutor(); //线程池只有一个线程
		for(int i=1;i<=10;i++){
			final int task = i;
			threadPool.execute(new Runnable(){
				@Override
				public void run() {
					for(int j=1;j<=10;j++){
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for  task of " + task);
					}
				}
			});
		}
		System.out.println("all of 10 tasks have committed! ");
		//threadPool.shutdownNow();
		
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(
				new Runnable(){
					@Override
				public void run() {
					System.out.println("bombing!");
					
				}},
				6,
				2,
				TimeUnit.SECONDS);
	}
}
