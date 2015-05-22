package com.zxyairings.codelib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.zxyairings.codelib.newfeatureJava5.EnumDemo;

//元注解：是注解的注解，为自定义注解服务
@Retention(RetentionPolicy.RUNTIME)//用来标示该自定义注解的生命周期
@Target({ElementType.METHOD,ElementType.TYPE})//用来标示该注解的作用对象：方法和类
public @interface ItcastAnnotation {
	String color() default "blue";
	String value();
	int[] arrayAttr() default {3,4,4};
	EnumDemo.TrafficLamp lamp() default EnumDemo.TrafficLamp.RED;
	MetaAnnotation annotationAttr() default @MetaAnnotation("lhm");
}
