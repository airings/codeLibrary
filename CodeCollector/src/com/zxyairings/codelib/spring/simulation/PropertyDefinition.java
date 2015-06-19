package com.zxyairings.codelib.spring.simulation;

//从xml读取的bean的property信息存放在该对象中

public class PropertyDefinition {

	private String name;
	private String ref;
	private String value;
	public String getName() {
		return name;
	}
	public PropertyDefinition(String name, String ref, String value) {
		this.name = name;
		this.ref = ref;
		this.value = value;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
