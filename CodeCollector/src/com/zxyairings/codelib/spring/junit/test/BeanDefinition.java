package com.zxyairings.codelib.spring.junit.test;

//将从xml读取的bean信息存放在这个对象中
public class BeanDefinition {

	private String id;
	private String className;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public BeanDefinition(String id, String className) {
		super();
		this.id = id;
		this.className = className;
	}
}
