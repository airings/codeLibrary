package com.zxyairings.codelib.spring.aop;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAOPTest {

	@Test public void interceptorTestAnnotation(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("beansAOPAnnotation.xml");
		PersonService personService = (PersonService)cxt.getBean("personService");
		personService.save("xx");
	}
	
	@Test public void interceptorTestXML(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("beansAOPXML.xml");
		PersonService personService = (PersonService)cxt.getBean("personService");
		personService.save("xx");
	}
}
