package com.zxyairings.codelib.spring.jdbc;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAOPTest {

	@Test public void jdbc(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("beansJDBC.xml");

	}

}
