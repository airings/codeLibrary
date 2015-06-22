package com.zxyairings.codelib.spring.junit.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zxyairings.codelib.spring.PersonService;
import com.zxyairings.codelib.spring.simulation.SimulateClassPathXMLApplicationContext;

//自动装配

public class SpringTest3 {

	
	@Test
	public void instanceSpring() {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans3.xml");
		PersonService ps = (PersonService)ctx.getBean("personService");
		ps.save();


	}
}
 