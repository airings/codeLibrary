package com.zxyairings.codelib.spring.impl;

import com.zxyairings.codelib.spring.PersonDao;

public class PersonDaoBean implements PersonDao {

	@Override
	public void add() {
		System.out.println("run PersonDaoBean's add method"); 
	}
}
