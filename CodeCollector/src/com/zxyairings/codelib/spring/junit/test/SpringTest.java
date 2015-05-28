package com.zxyairings.codelib.spring.junit.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zxyairings.codelib.spring.PersonService;

public class SpringTest {

	@Test
	public void instanceSpring() {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		SimulateClassPathXMLApplicationContext ctx = new SimulateClassPathXMLApplicationContext("beans.xml");
		PersonService personService = (PersonService)ctx.getBean("personService");
		personService.save();
//		fail("Not yet implemented");
	}

}
