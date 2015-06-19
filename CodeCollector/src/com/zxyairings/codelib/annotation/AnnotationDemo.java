package com.zxyairings.codelib.annotation;

import java.lang.reflect.Method;
import java.util.Arrays;

/*
 * 下面才是真正的使用注解-自定义注解
 * 注解就相当于一个你的源程序中要调用的一个类，要在源程序中应用某个注解，得先准备好了这个注解类。就像你要调用某个类，得先有开发号这个类。
 * 
 * 注解相当于一种标记，在程序中加了注解就等于为程序打上了某种标记，没加，则等于没有某种标记，
 * 以后，javac编译器，开发工具和其他程序可以用反射来了解你的类及各种元素上有无何种标记，
 * 看你有什么标记，就去干相应的事。
 * 标记可以加在包，类，字段，方法，方法的参数以及局部变量上。
 * 
	看java.lang包，可看到JDK中提供的最基本的annotation。
	 * SuppressWarnings
	 * Deprecated
	 * Override
 * 
 * */

//应用了注解类@ItcastAnnotation的类AnnotationDemo
@ItcastAnnotation(annotationAttr=@MetaAnnotation("flx"),color="red",value="abc",arrayAttr=8)//如果数组属性中只有一个元素，这时候属性值部分可以省略大括
public class AnnotationDemo {

	/**
	 * @param args
	 */
	//一个注解就相当于一个类，这里我们在使用注解，就相当于创建了一个注解的实例对象。
	@SuppressWarnings("deprecation")
	@ItcastAnnotation("xyz") //如果注解中有一个名称为value的属性，且你只想设置value属性（即其他属性都采用默认值或者你只有一个value属性），那么可以省略value=部分，例如：@MyAnnotation("lhm")。
	public static void main(String[] args) throws Exception{
//		对“应用了注解类的类”进行反射操作的类，这里放在main方法中，其实应该将这里的main方法放在另一个类中。

		System.runFinalizersOnExit(true);
		
		//通过反射来检查一个类有没有加自定义的注解，进而采取相应的动作
		if(AnnotationDemo.class.isAnnotationPresent(ItcastAnnotation.class)){//如果注解使用默认值，那么这个判断返回false，原因是ItcastAnnotation.class是字节码，它对应的注解生命周期是RUNTIME
			System.out.println("***AnnotationDemo class's annotation***");
			ItcastAnnotation annotation = (ItcastAnnotation)AnnotationDemo.class.getAnnotation(ItcastAnnotation.class);//得到注解对象。只要在一个类加上注解，就会产生一个注解对象。
			System.out.println(annotation.color());//取到注解的属性值
			System.out.println(annotation.value());
			System.out.println(annotation.arrayAttr().length);
			System.out.println(annotation.arrayAttr());
			System.out.println(Arrays.asList(annotation.arrayAttr()));
			System.out.println(annotation.lamp().nextLamp().name());
			System.out.println(annotation.annotationAttr().value());
		}
		
		System.out.println("***main method's annotation***");
		Method mainMethod = AnnotationDemo.class.getMethod("main", String[].class);
		ItcastAnnotation annotation2 = (ItcastAnnotation)mainMethod.getAnnotation(ItcastAnnotation.class);
		System.out.println(annotation2.value());
	}

	@Deprecated
	public static void sayHello(){
		System.out.println("hi,传智播客");
	}
}