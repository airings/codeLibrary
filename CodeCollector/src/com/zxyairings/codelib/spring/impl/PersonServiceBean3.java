package com.zxyairings.codelib.spring.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.xml.stream.events.Comment;

import org.jaxen.function.FalseFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.zxyairings.codelib.spring.PersonDao;
import com.zxyairings.codelib.spring.PersonService;
import com.zxyairings.codelib.spring.simulation.ItcastResource;

@Service("personService")
@Scope("prototype")
public class PersonServiceBean3 implements PersonService {
	private PersonDao personDao;
	
//	public PersonServiceBean3(PersonDao personDao) {
//		this.personDao = personDao;
//	}
//	
//	
//	public PersonDao getPersonDao() {
//		return personDao;
//	}

	@PostConstruct
	public void init(){
		System.out.println("init");
	}
	
//	Comment @Scope("prototype") 即单例情况下，在关闭spring容器之前才会调用destroy方法
	@PreDestroy
	public void destory(){
		System.out.println("destory");
	}
	
	
//	自动装配byName, byType, 都需要有set方法，不然无法注入
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	public void save() {
		System.out.print("PersonServiceBean3");
		personDao.add();
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
