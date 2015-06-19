package com.zxyairings.codelib.spring.simulation;

import java.util.ArrayList;
import java.util.List;

//将从xml读取的bean信息存放在这个对象中
public class BeanDefinition {

	private String id;
	private String className;
	private List<PropertyDefinition> properties = new ArrayList<PropertyDefinition>(); 
	public String getId() {
		return id;
	}
	public List<PropertyDefinition> getProperties() {
		return properties;
	}
	public void setProperties(List<PropertyDefinition> properties) {
		this.properties = properties;
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
