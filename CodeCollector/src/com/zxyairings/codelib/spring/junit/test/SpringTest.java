package com.zxyairings.codelib.spring.junit.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zxyairings.codelib.spring.PersonService;
import com.zxyairings.codelib.spring.simulation.SimulateClassPathXMLApplicationContext;

/*
 * 单例bean是在容器实例化的时候被实例化的
 * 如果scope是prototye那么该bean是在容器调用getBean方法的时候被实例化的
 * 那么这种行为可以更改么？可以，通过bean/beans的属性lazy-init
 * 
 * 单例bean：
 * 		lazy-init="false":单例bean是在容器实例化的时候被实例化的
 * 		lazy-init="true":单例bean是在容器实例化的时候被实例化的
 * 
 */


public class SpringTest {

	@Test
	public void instanceSpring() {
//		单例bean是在容器实例化的时候被实例化的
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		
//		模拟容器
//		SimulateClassPathXMLApplicationContext ctx = new SimulateClassPathXMLApplicationContext("beans.xml");
		
//		如果scope是prototye那么该bean是在容器调用getBean方法的时候被实例化的
		PersonService personService1 = (PersonService)ctx.getBean("personService");
		PersonService personService2 = (PersonService)ctx.getBean("personService");
		System.out.println(personService1==personService2);
		personService1.save();

		System.out.println("=======set=========");
		System.out.println(personService1.getSets().getClass().getName());
		for (String value : personService1.getSets()) {
			System.out.println(value);
		}
		System.out.println("=======list=========");
		System.out.println(personService1.getLists().getClass().getName());
		for (String value : personService1.getLists()) {
			System.out.println(value);
		}
		System.out.println("=======properties=========");
		System.out.println(personService1.getProperties().getClass().getName());
		for (Object key : personService1.getProperties().keySet()) {
			System.out.println(key+"="+personService1.getProperties().getProperty((String)key));
		}
		System.out.println("=======map=========");
		System.out.println(personService1.getMaps().getClass().getName());
		for (String key : personService1.getMaps().keySet()) {
			System.out.println(key+"="+personService1.getMaps().get(key));
		}
		
//		ctx.close();

//		fail("Not yet implemented");
	}

}
