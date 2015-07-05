package com.zxyairings.codelib.jdbc.dao.refactor;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zxyairings.codelib.jdbc.domain.User;

/*
 * 策略模式：使用接口，组合和参入传入的方式来实现
 * 
 * 模板方法：使用抽象类，继承的方式来实现
 * 
 * 这是策略模式。
 * 
 * 对比UserDaoImpl.java
 */

public class UserDaoImplStrategy {
	MyDaoTemplate template = new MyDaoTemplate();

	public User findUser(String loginName, String password) {
		String sql = "select id, name, money, birthday  from user where name=?";
		Object[] args = new Object[] { loginName };
		RowMapper mapper = new UserRowMapper();
		Object user = this.template.find(sql, args, mapper);
		return (User) user;
	}

//	使用匿名类
	public String findUserName(int id) {
		String sql = "select name from user where id=?";
		Object[] args = new Object[] { id };
		Object name = this.template.find(sql, args, new RowMapper() {

			public Object mapRow(ResultSet rs) throws SQLException {
				return rs.getString("name");
			}
		});
		return (String) name;
	}
}

class UserRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMoney(rs.getFloat("money"));
		user.setBirthday(rs.getDate("birthday"));
		return user;
	}

}