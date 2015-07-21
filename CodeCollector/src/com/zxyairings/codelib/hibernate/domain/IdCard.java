package com.zxyairings.codelib.hibernate.domain;

/**
 * 身份证
 * 
 * @author tyg
 * 
 */
public class IdCard {
	private Integer id;
	private String number;

	private Person person; // 关联的公民

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "[IdCard：id=" + id + ", number=" + number + "]";
	}

}
