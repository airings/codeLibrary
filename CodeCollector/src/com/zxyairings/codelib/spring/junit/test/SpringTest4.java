package com.zxyairings.codelib.spring.junit.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zxyairings.codelib.spring.PersonService;
import com.zxyairings.codelib.spring.simulation.SimulateClassPathXMLApplicationContext;

//自动装配

public class SpringTest4 {

	
	@Test
	public void instanceSpring() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("beansAutoScan.xml");
		PersonService ps = (PersonService)ctx.getBean("personService");
		PersonService ps1 = (PersonService)ctx.getBean("personService");
		System.out.println(ps==ps1);
		ctx.close();
	}
}
 