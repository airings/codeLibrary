package com.zxyairings.codelib.spring.impl;

import org.springframework.stereotype.Repository;

import com.zxyairings.codelib.spring.PersonDao;

@Repository
public class PersonDaoBean implements PersonDao {

	@Override
	public void add() {
		System.out.println("run PersonDaoBean's add method"); 
	}
}
