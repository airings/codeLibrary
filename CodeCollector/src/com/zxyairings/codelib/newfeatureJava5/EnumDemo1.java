package com.zxyairings.codelib.newfeatureJava5;

public class EnumDemo1 {

}

//普通类的形式来模拟java中的枚举
abstract class WeekDay1 {
	private WeekDay1(){} //别人不能随便new对象，但是此类中可以创建对象
	
//	public final static WeekDay1 SUN = new WeekDay1();
	public final static WeekDay1 SUN = new WeekDay1(){

		@Override
		public WeekDay1 nextDay() {
			// TODO Auto-generated method stub
			return MON;
		}
		
	};
	
//	public final static WeekDay1 MON = new WeekDay1();
	public final static WeekDay1 MON = new WeekDay1(){

		@Override
		public WeekDay1 nextDay() {
			// TODO Auto-generated method stub
			return SUN;
		}
		
	};	
	//采用抽象方法定义nextDay就讲大量的if-else语句转移成一个个独立的类。
	public abstract WeekDay1 nextDay();
	
	//一种写法如下，将很多的if-else在父类中实现，另一种就是将nextDay方法分别在子类中实现，如上。
	
/*	public WeekDay nextDay(){
		if(this == SUN){
			return  MON;
		}else{
			return SUN;
		}
	}
*/
	
	public String toString(){
		return this==SUN?"SUN":"MON";
	}
}