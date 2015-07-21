package com.zxyairings.codelib.hibernate.domain;
/*
 * 对持久化对象的要求：前3为强制的，后2为optional
 * 
 * 1. 提供一个无参构造器。使Hibernate可以使用Constructor.newInstance()来实例化持久化类。
 * 2. 提供一个标识属性(identifier property)。通常映射为数据库表的主键字段。如果没有该属性，一些功能将不起作用，如，Session.saveOrUpdate()
 * 3. 为持久化类的字段声明访问方法(get/set)。Hibernate对Javabeans风格的属性实行持久化
 * 4. 使用非final类。在运行时生成代理是Hibernate的一个重要功能。如果持久化类没有实现任何接口，Hibernate使用CGLB生成代理。如果使用的是final类，则无法生成CGLB代理，会影响懒加载的特性。
 * 5. 重写equals和hashCode方法。如果需要把持久化类的实例放到Set中(当需要进行关联映射时)，则应该重写这两个方法。
 * 
 * OID：为了在系统中能够找到所需的对象，需要为每一个对象分配一个唯一的标识号。在关系数据库中称为主键，而在对象术语中，称作对象标识(Object Identifier - OID)
 */
public class User {

	private int id;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}
