package com.zxyairings.codelib.spring.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.jaxen.function.FalseFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.zxyairings.codelib.spring.PersonDao;
import com.zxyairings.codelib.spring.PersonService;
import com.zxyairings.codelib.spring.simulation.ItcastResource;

/*
这两个注解的区别是：
@Autowired 默认按类型装配，
@Resource默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
*/

public class PersonServiceBean2 implements PersonService {
	
//	@Resource(name="personDaoxxxx")
//	@ItcastResource
	@Autowired @Qualifier("personDaoxxxx") //如果我们想使用按名称装配，可以结合@Qualifier注解一起使用。
	private PersonDao personDao;//最优雅的注入方式，推荐使用
	private PersonDao personDao2;
	private String name;//适合xml注入，不适合注解注入
	
//	@Resource注解和@Autowired一样，也可以标注在字段或属性的setter方法上，但它默认按名称装配。
//	@Resource
//	@ItcastResource
	@Autowired(required=false)//@Autowired注解是按类型装配依赖对象，默认情况下它要求依赖对象必须存在，如果允许null值，可以设置它required属性为false。
	public void setPersonDao2(PersonDao personDao2) {
		this.personDao2 = personDao2;
	}
	
	
	@Override
	public void save() {
		System.out.print("PersonServiceBean2");
		personDao.add();
		personDao2.add();
		
	}

	@Override
	public Set<String> getSets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLists() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getMaps() {
		// TODO Auto-generated method stub
		return null;
	}

}
