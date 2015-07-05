package com.zxyairings.codelib.jdbc.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.zxyairings.codelib.jdbc.domain.User;
import com.zxyairings.codelib.jdbc.util.JdbcUtilsDBCP;

/*
 * NamedParameterJdbcTemplate内部包含了一个JdbcTemplate，所以JdbcTemplate能做的事情NamedParameterJdbcTemplate都能干； 
 * NamedParameterJdbcTemplate相对于JdbcTemplate主要增加了参数可以命名的功能。
 */

public class NamedJdbcTemplateDemo {
	static NamedParameterJdbcTemplate named = new NamedParameterJdbcTemplate(
			JdbcUtilsDBCP.getDataSource());

	public static void main(String[] args) {
		User user = new User();
		user.setMoney(10);
		user.setId(2);
		System.out.println(findUser1(user));
	}

	//通过KeyHolder拿到插入数据的主键值
	static void addUser(User user) {
		String sql = "insert into user(name,birthday, money) values (:name,:birthday,:money) ";
		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		named.update(sql, ps, keyHolder);
		int id = keyHolder.getKey().intValue();//这样得到的主键是整型的
		user.setId(id);
		
//		Map map = keyHolder.getKeys();//当主键是复合主键时或者主键不是整型时
	}

	//可以给?命名, 这里通过map来设置参数的值
	static User findUser(User user) {
		String sql = "select id, name, money, birthday  from user "
				+ "where money > :m and id < :id";
		Map params = new HashMap();
		params.put("m", user.getMoney());
		params.put("id", user.getId());
		Object u = named.queryForObject(sql, params, new BeanPropertyRowMapper(
				User.class));
		return (User) u;
	}

	static User findUser1(User user) {
		String sql = "select id, name, money, birthday  from user "
				+ "where money > :money and id < :id"; //使用参数元的话，参数命名是由规定的，跟javabean一样
		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
		Object u = named.queryForObject(sql, ps, new BeanPropertyRowMapper(
				User.class));
		return (User) u;
	}
}
