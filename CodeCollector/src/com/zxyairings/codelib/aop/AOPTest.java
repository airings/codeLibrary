package com.zxyairings.codelib.aop;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zxyairings.codelib.spring.PersonDao;

public class AOPTest {

	@Test
	public void proxyTestJDK() {
		JDKProxyFactory jdkProxyFactory = new JDKProxyFactory();
//		PersonService service = (PersonService)jdkProxyFactory.createProxyInstance(new PersonServiceBean("yyyy"));
		PersonService service = (PersonService)jdkProxyFactory.createProxyInstance(new PersonServiceBean());
		service.save("888");
		
	}
	
	@Test
	public void proxyTestcglib() {
		CGlibProxyFactory cglibProxyFactory = new CGlibProxyFactory();
		PersonServiceBean service = (PersonServiceBean)cglibProxyFactory.createProxyInstance(new PersonServiceBean());
//		PersonServiceBean service = (PersonServiceBean)cglibProxyFactory.createProxyInstance(new PersonServiceBean("cccc"));
		service.save("888");
		
	}

}
