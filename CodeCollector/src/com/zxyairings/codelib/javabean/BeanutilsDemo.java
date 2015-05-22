package com.zxyairings.codelib.javabean;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanutilsDemo {
	public static void main(String[] args) throws Exception {
		ReflectPoint pt1 = new ReflectPoint(3,5);

		
		System.out.println(BeanUtils.getProperty(pt1, "x"));
//		System.out.println(BeanUtils.getProperty(pt1, "x").getClass().getName());//字符串
		BeanUtils.setProperty(pt1, "x", "9");//"9" 是字符串
		System.out.println(pt1.getX());

		//BeanUtils可以操作属性的属性-属性链
		BeanUtils.setProperty(pt1, "birthday.time", "123");
		System.out.println(BeanUtils.getProperty(pt1, "birthday.time"));
		
		/*
		//java7的新特性
		Map map = {name:"zxx",age:18};
		//BeanUtils还可以操作map
		BeanUtils.setProperty(map, "name", "lhm");
		*/
		
		//BeanUtils还可以进行map和javaBean之间的相互转换
		
		//PropertyUtils也是一个操作JavaBean的工具类
		//BeanUtils是以字符串的形式来对javaBean操作,其内部会根据属性类型自动进行类型转换
		//PropertyUtils是以属性原来的类型来对javaBean操作
		PropertyUtils.setProperty(pt1, "x", 9);//这里9是int
		System.out.println(PropertyUtils.getProperty(pt1, "x").getClass().getName());
		
	}
}
