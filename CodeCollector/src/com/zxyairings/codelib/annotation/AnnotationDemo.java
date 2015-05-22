package com.zxyairings.codelib.annotation;

import java.lang.reflect.Method;

/*
 * java 有三个基本的注解：
 * SuppressWarnings
 * Deprecated
 * Override
 * 
 * 下面才是真正的使用注解-自定义注解
 * 注解就相当于一个你的源程序中要调用的一个类，要在源程序中应用某个注解，得先准备好了这个注解类。就像你要调用某个类，得先有开发号这个类。
 * 
 * 
 * 
 * */

@ItcastAnnotation(annotationAttr=@MetaAnnotation("flx"),color="red",value="abc",arrayAttr=1)
public class AnnotationDemo {

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	@ItcastAnnotation("xyz")
	public static void main(String[] args) throws Exception{
		
		System.runFinalizersOnExit(true);
		
		//通过反射来检查一个类有没有加自定义的注解，进而采取相应的动作
		if(AnnotationDemo.class.isAnnotationPresent(ItcastAnnotation.class)){
			ItcastAnnotation annotation = (ItcastAnnotation)AnnotationDemo.class.getAnnotation(ItcastAnnotation.class);//得到注解对象。只要在一个类加上注解，就会产生一个注解对象。
			System.out.println(annotation.color());
			System.out.println(annotation.value());
			System.out.println(annotation.arrayAttr().length);
			System.out.println(annotation.lamp().nextLamp().name());
			System.out.println(annotation.annotationAttr().value());
		}
		
		Method mainMethod = AnnotationDemo.class.getMethod("main", String[].class);
		ItcastAnnotation annotation2 = (ItcastAnnotation)mainMethod.getAnnotation(ItcastAnnotation.class);
		System.out.println(annotation2.value());
	}

	@Deprecated
	public static void sayHello(){
		System.out.println("hi,传智播客");
	}
}