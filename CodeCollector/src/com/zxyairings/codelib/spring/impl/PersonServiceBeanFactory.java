package com.zxyairings.codelib.spring.impl;
/*
Spring三种create bean 方式

1.使用类构造器实例化
<bean id=“orderService" class="cn.itcast.OrderServiceBean"/>
2.使用静态工厂方法实例化
<bean id="personService" class="cn.itcast.service.OrderFactory" factory-method="createOrder"/>
public class OrderFactory {
	public static OrderServiceBean createOrder(){
		return new OrderServiceBean();
	}
}
3.使用实例工厂方法实例化:
<bean id="personServiceFactory" class="cn.itcast.service.OrderFactory"/>
<bean id="personService" factory-bean="personServiceFactory" factory-method="createOrder"/>
public class OrderFactory {
	public OrderServiceBean createOrder(){
		return new OrderServiceBean();
	}
}
*/
public class PersonServiceBeanFactory {
	public static PersonServiceBean createPersonServiceBean(){
		return new PersonServiceBean();
	}
	
	public PersonServiceBean createPersonServiceBean2(){
		return new PersonServiceBean();
	}
}