package com.zxyairings.codelib.others;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


//quartz是第三方工具，可以根据日期来设置Timer

public class TraditionalTimerDemo {
	private static int count = 0;
	public static void main(String[] args) {
		//Timer基础实例：
/*		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("bombing!");
				
			}
		}, 
		10000, //delay
		3000); //period
*/
		
		//子母弹连环炸：每隔2s炸一次
		class MyTimerTask1 extends TimerTask{
			
			@Override
			public void run() {
				count = (count+1)%2;
				System.out.println("bombing!");
				new Timer().schedule(/*new TimerTask() {
					
					@Override
					public void run() {
						System.out.println("bombing!");
					}
				}*/new MyTimerTask1(),2000);
			}
		}
		new Timer().schedule(new MyTimerTask1(), 2000);
		
		//子母弹交替炸，一会是2s，一会是4s
		class MyTimerTask extends TimerTask{
//			static int count =0;//内部类不能声明静态变量
			@Override
			public void run() {
				count = (count+1)%2;
				System.out.println("bombing!");
				new Timer().schedule(new MyTimerTask(),2000+2000*count);
			}
		}
		
		new Timer().schedule(new MyTimerTask(), 2000);
		
		//打印出当前是多少秒
		while(true){
			System.out.println(new Date().getSeconds());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
