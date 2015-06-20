package com.zxyairings.codelib.spring.impl;


import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import com.zxyairings.codelib.spring.PersonDao;
import com.zxyairings.codelib.spring.PersonService;

/*
这两个注解的区别是：
@Autowired 默认按类型装配，
@Resource默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
*/

public class PersonServiceBean implements PersonService {
	private PersonDao personDao;
	private String name;
	private Integer id;
	private PersonDao personDao2;
	private String name2;
	
//	如果没有指定name属性，并且按照默认的名称(即变量名 personDao3)仍然找不到依赖对象时， @Resource注解会回退到按类型装配。但一旦指定了name属性，就只能按名称装配了。
	@Resource(name="personDao3") 
	private PersonDao personDao3;
	
	private PersonDao personDao4;
	
	@Resource(name="personDaoxxx")
	public void setPersonDao4(PersonDao personDao4) {
		this.personDao4 = personDao4;
	}

	
//	使用构造器装配属性
	public PersonServiceBean(PersonDao personDao2, String name2) {
		this.personDao2 = personDao2;
		this.name2 = name2;
	}

	private Set<String> sets;
	private List<String> lists;
	private Properties properties;
	private Map<String, String> maps;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void init() {
		System.out.println("I am initial method");
	}
	
//	要想知道bean到底是在什么时候实例化的，最简单的方法就是在默认构造器中输入信息
	public PersonServiceBean() {
		System.out.println("I am instanced!");
	}

	@Override
	public void save() {
		System.out.println("this is save method");
		personDao.add();
		System.out.println("name: "+getName());
		System.out.println("id: "+id);
		personDao2.add();
		System.out.println("name2: "+getName());
		personDao3.add();
		personDao4.add();
	}
	
	public void destroy() {
		System.out.println("I am destroy method");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getSets() {
		return sets;
	}

	public void setSets(Set<String> sets) {
		this.sets = sets;
	}

	public List<String> getLists() {
		return lists;
	}

	public void setLists(List<String> lists) {
		this.lists = lists;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Map<String, String> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}
}
