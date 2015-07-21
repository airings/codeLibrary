package com.zxyairings.codelib.hibernate.domain;
/*

 */
public class User3 {

	// private Integer id; // 0, null
	private String id;
	private String name;

	// public Integer getId() { return id; }
	// public void setId(Integer id) { this.id = id; }

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}
