package com.zxyairings.codelib.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.zxyairings.codelib.jdbc.domain.User;

//JDBC 李勇 讲的反射

//java所有的灵活性都是通过反射来实现的

//这里介绍反射主要是关于在JDBC中的应用，它所关心的关键知识点是：
//1.通过类 构造出一个实例
//2.根据方法名来调用类的方法

//我们利用这些反射技术的目的是，将jdbc的结果集封装到domain对象

public class ReflectForJDBC {
	
	public static void main(String[] args) throws Exception {
		Class clazz = User.class;
		// clazz = Bean.class;
		Object obj = create(clazz);
		System.out.println(obj);
		
		System.out.println("---------");
		invoke1(obj, "showName");

		System.out.println("---------");
		field(clazz);
	}

	static Object create(Class clazz) throws Exception {
		Constructor con = clazz.getConstructor(String.class);
		Object obj = con.newInstance("test name");
		return obj;
	}

	static void invoke1(Object obj, String methodName)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, Exception, NoSuchMethodException {
		Method[] ms = obj.getClass().getDeclaredMethods();//public，private方法都可以得到，得不到超类的方法
		ms = obj.getClass().getMethods();//得到超类和本类的public方法
		//通过方法名来定位一个方法
		for (Method m : ms) {
			// System.out.println(m.getName());
			if (methodName.equals(m.getName()))
				m.invoke(obj, null);
		}

		//当需要很精确的定位到某一个方法时，需要用参数列表和方法名来定位
		Method m = obj.getClass().getMethod(methodName, null);
		m.invoke(obj, null);
	}

	static void field(Class clazz) throws Exception {
		//不建议使用Field这个类，因为面向对象思想要求通过方法来操作属性，而不是直接操作属性
		Field[] fs = clazz.getDeclaredFields(); // 跟方法的类似
		//fs = clazz.getFields();
		for (Field f : fs)
			System.out.println(f.getName());
	}
	
	static void annon(Class clazz) throws Exception {
		Annotation[] as = clazz.getAnnotations();
	}

}
