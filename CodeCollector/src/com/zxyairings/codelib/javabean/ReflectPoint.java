package com.zxyairings.codelib.javabean;

import java.util.Date;

public class ReflectPoint {
	private Date birthday = new Date();//需要赋初值，否则BeanUtils调用get方法后在set时是空指针异常。
	private int x;
	private int y;
	
	public ReflectPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
