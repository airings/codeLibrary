package com.zxyairings.codelib.jdbc.spring;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.zxyairings.codelib.jdbc.domain.User;
import com.zxyairings.codelib.jdbc.util.JdbcUtilsDBCP;

//这种使用方法所返回的结果就是正确的对象，它是使用了泛型。不用在将Object转型了
/*
 * SimpleJdbcTemplate内部包含了一个NamedParameterJdbcTemplate;
 * 所以NamedParameterJdbcTemplate能做的事情SimpleJdbcTemplate都能干，
 * SimpleJdbcTemplate相对于NamedParameterJdbcTemplate主要增加了JDK5.0的泛型和可变长度参数支持。
 */

public class SimpleJdbcTemplateDemo {
	static SimpleJdbcTemplate simple = new SimpleJdbcTemplate(JdbcUtilsDBCP
			.getDataSource());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static User find(String name) {
		String sql = "select id, name, money, birthday  from user where name=? and money > ?";
		User user = simple.queryForObject(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(User.class),
				name, 100f);
		return user;
	}

}
