package com.zxyairings.codelib.javabean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * IntroSpectorSimpleDemo 是使用内省的简单方法
 * 
 * IntroSpectorComplexDemo 是使用内省的复杂方法
 * 		采用遍历BeanInfo的所有属性方式来查找和设置某个RefectPoint对象的x属性。
 * 		在程序中把一个类当作JavaBean来看，就是调用IntroSpector.getBeanInfo方法， 
 * 		得到的BeanInfo对象封装了把这个类当作JavaBean看的结果信息。
 * 
 * 如果是想得到javabean的某一个属性的值就使用简单方法IntroSpectorSimpleDemo
 * 如果是想得到一个javabean所有属性的值就是用复杂方法IntroSpectorComplexDemo，因为IntroSpector.getBeanInfo返回一个属性数组，我们需要遍历它找到我们要德属性。
 * 
 * 在实际工作中，BeanUtils比内省更常用，要熟练掌握。内省只要见识过，知道就可以了。
 * 
 * */

public class IntroSpectorComplexDemo {
	
	public static void main(String[] args) throws Exception {
		
		ReflectPoint pt1 = new ReflectPoint(3,5);
		
		String propertyName = "x";
		//"x"-->"X"-->"getX"-->MethodGetX-->
		Object retVal = getProperty(pt1, propertyName);
		System.out.println(retVal);
		
		Object value = 7;
		
		setProperties(pt1, propertyName, value);
	}

	private static void setProperties(Object pt1, String propertyName,
			Object value) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		PropertyDescriptor pd2 = new PropertyDescriptor(propertyName,pt1.getClass());
		Method methodSetX = pd2.getWriteMethod();
		methodSetX.invoke(pt1,value);
	}

	private static Object getProperty(Object pt1, String propertyName)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		/*
		 * 简便方式
		PropertyDescriptor pd = new PropertyDescriptor(propertyName,pt1.getClass());//得到某一个属性
		Method methodGetX = pd.getReadMethod();
		Object retVal = methodGetX.invoke(pt1);
		 
		 */
		//麻烦方式
		BeanInfo beanInfo =  Introspector.getBeanInfo(pt1.getClass());
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();//得到所有的属性
		Object retVal = null;
		for(PropertyDescriptor pd : pds){//遍历所有的属性
			if(pd.getName().equals(propertyName))
			{
				Method methodGetX = pd.getReadMethod();
				retVal = methodGetX.invoke(pt1);
				break;
			}
		}
		return retVal;
	}

}
