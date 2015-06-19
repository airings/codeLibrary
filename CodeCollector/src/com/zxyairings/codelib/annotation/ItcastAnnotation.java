package com.zxyairings.codelib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.zxyairings.codelib.newfeatureJava5.EnumDemo;

//注解类

/*
 * @Retention元注解，表示注解类的生命周期
 * 其三种取值：
 * 		RetetionPolicy.SOURCE
 * 							 <-- source是给javac编译器看的，
 * 		RetetionPolicy.CLASS
 * 							 <-- class是给类加载器看的
 * 		RetetionPolicy.RUNTIME
 * 							 <-- runtime是给运行时看的
 * 其含义是保存annotation到这三个值为止。例如ItcastAnnotation注解，将保留此注解一直到运行期间
 * 分别对应：java源文件-->class文件-->内存中的字节码。默认值是RetetionPolicy.CLASS
 * 
 * javac将java源文件编译成class文件时，会做一些处理，决定是否保留注解；
 * 当类加载器加载class文件时，也会对class文件做一些处理，决定是否保留注解。
 * 这里需要注意，class文件中的内容不是字节码，只有在类加载器加载到内存中，并作一些处理以后的内存中的二进制内容才是字节码。
 * 
 * @Override、@SuppressWarnings和@Deprecated这三个注解的属性值分别是什么？可以通过java API 来查看
 * RetetionPolicy.SOURCE，RetetionPolicy.SOURCE，RetetionPolicy.RUNTIME
 *
 *
 * @Target元注解: 表示注解类可以作用的目标
 * Target的默认值为任何元素，设置Target等于ElementType.METHOD，原来加在类上的注解就报错了，
 * 改为用数组方式设置{ElementType.METHOD,ElementType.TYPE}就可以了。看ElementType的API
 * */

//元注解：是注解的注解，为自定义注解服务。有两种元注解。
@Retention(RetentionPolicy.RUNTIME)//用来标示该自定义注解的生命周期
@Target({ElementType.METHOD,ElementType.TYPE})//用来标示该注解的作用对象：方法和类
public @interface ItcastAnnotation {
	/*
	 * 为注解增加基本属性
	 * 注解相当于接口，属性相当于方法。
	 * 
	 * 什么是注解的属性？
	 * 一个注解相当于一个胸牌，如果你胸前贴了胸牌，就是传智播客的学生，否则，就不是。
	 * 如果还想区分出是传智播客哪个班的学生，这时候可以为胸牌在增加一个属性来进行区分。
	 * 加了属性的标记效果为：@MyAnnotation(color="red")
	 * 
	 * */
	String color() default "blue";
	String value();
	int[] arrayAttr() default {3,4,4};
	EnumDemo.TrafficLamp lamp() default EnumDemo.TrafficLamp.RED; //枚举类型的属性
	MetaAnnotation annotationAttr() default @MetaAnnotation("lhm"); //注解类型的属性
}
