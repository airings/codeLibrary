package com.zxyairings.codelib.jdbc.dao.impl;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.zxyairings.codelib.jdbc.dao.UserDao;
import com.zxyairings.codelib.jdbc.domain.User;
import com.zxyairings.codelib.jdbc.util.JdbcUtilsDBCP;

public class UserDaoSpringImpl implements UserDao {
	private SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(
			JdbcUtilsDBCP.getDataSource());

	public void addUser(User user) {
		String sql = "insert into user (name, money, birthday) values (:name, :money, :birthday)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql,
				param, keyHolder);
		user.setId(keyHolder.getKey().intValue());
	}

	public void delete(User user) {
		String sql = "delete from user where id=?";
		this.simpleJdbcTemplate.update(sql, user.getId());
	}

	public User findUser(String loginName, String password) {
		String sql = "select id, name, money, birthday  from user where name=?";
		return this.simpleJdbcTemplate.queryForObject(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(User.class),
				loginName);
	}

	public User getUser(int userId) {
		String sql = "select id, name, money, birthday  from user where id=?";
		return this.simpleJdbcTemplate.queryForObject(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(User.class),
				userId);
	}

	public void update(User user) {
		String sql = "update user set name=?, birthday=?, money=? where id=? ";
		this.simpleJdbcTemplate.update(sql, user.getName(), user.getBirthday(),
				user.getMoney(), user.getId());

//		使用命名参数的方式
		sql = "update user set name=:name, birthday=:birthday, money=:money where id=:id ";
		this.simpleJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(
				user));
	}

}