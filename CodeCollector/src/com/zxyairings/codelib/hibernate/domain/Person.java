package com.zxyairings.codelib.hibernate.domain;

/**
 * 公民
 * 
 * @author tyg
 * 
 */
public class Person {
	private Integer id;
	private String name;

	private IdCard idCard; // 关联的身份证

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IdCard getIdCard() {
		return idCard;
	}

	public void setIdCard(IdCard idCard) {
		this.idCard = idCard;
	}

	@Override
	public String toString() {
		return "[Person：id=" + id + ", name=" + name + "]";
	}

}
