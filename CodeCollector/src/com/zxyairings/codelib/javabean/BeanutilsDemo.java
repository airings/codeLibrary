package com.zxyairings.codelib.javabean;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/*
 * 因为对javabean的操作如此的频繁，Apache就做了一个第三方工具包，来对javabean进行取值和设值的操作
 * 
 * 跟内省完成相同的工作，但是更方便，对比内省的实例就会发现，其实BeanUtils封装了getProperty,setProperty等方法，
 * 而且还包含很多实用的方法，例如属性链，javabean与map之间的相互转换。
 * 
 * 在实际工作中，BeanUtils比内省更常用，要熟练掌握。内省只要见识过，知道就可以了。
 */

public class BeanutilsDemo {
	public static void main(String[] args) throws Exception {
		ReflectPoint pt1 = new ReflectPoint(3,5);

		
		System.out.println(BeanUtils.getProperty(pt1, "x"));
//		System.out.println(BeanUtils.getProperty(pt1, "x").getClass().getName());//字符串
		//set的是字符串9，get返回的也是是字符串9，但是javabean内部是int9，这就是BeanUtils的方便之处，不用再进行类型转换。
		BeanUtils.setProperty(pt1, "x", "9");//"9" 是字符串
		System.out.println(pt1.getX());

		//BeanUtils可以操作属性的属性-属性链
		//Date对象上有一个setTime方法，所以可以讲Date看成javabean看待。
		//这就是属性链：ReflectPoint -> Date -> time
		BeanUtils.setProperty(pt1, "birthday.time", "123");//会报异常“java.lang.IllegalArgumentException: No bean specified”，需要对ReflectPoint中的birthday赋初值。
		System.out.println(BeanUtils.getProperty(pt1, "birthday.time"));
		
		//BeanUtils还可以进行map和javaBean之间的相互转换
//		BeanUtils.describe(pt1);//javabean -> map
//		BeanUtils.populate(pt2, map);// map -> javabean
		
		/*
		//java7的新特性
		Map map = {name:"zxx",age:18};
		*/
		//BeanUtils还可以操作map
//		BeanUtils.setProperty(map, "name", "lhm");


		//PropertyUtils也是一个操作JavaBean的工具类
		//BeanUtils是以字符串的形式来对javaBean操作,其内部会根据属性类型自动进行类型转换
		//PropertyUtils是以属性原来的类型来对javaBean操作
		PropertyUtils.setProperty(pt1, "x", 9);//这里9是int
		System.out.println(PropertyUtils.getProperty(pt1, "x").getClass().getName());
		
	}
}
