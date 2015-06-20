package com.zxyairings.codelib.spring.junit.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zxyairings.codelib.spring.PersonService;
import com.zxyairings.codelib.spring.simulation.SimulateClassPathXMLApplicationContext;

public class SpringTest2 {

	@Test
	public void instanceSpring() {
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans2.xml");
		
//		模拟容器
		SimulateClassPathXMLApplicationContext ctx = new SimulateClassPathXMLApplicationContext("beans2.xml");
		
		PersonService ps = (PersonService)ctx.getBean("personService");
		ps.save();

	}
}
