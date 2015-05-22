package com.zxyairings.codelib.javabean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
		for(PropertyDescriptor pd : pds){
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
