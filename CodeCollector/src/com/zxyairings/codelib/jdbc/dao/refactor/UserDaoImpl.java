package com.zxyairings.codelib.jdbc.dao.refactor;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zxyairings.codelib.jdbc.domain.User;
/*
 * 
 * 这是使用传参数和模板方法模式来改进原来的DAO，具有一定的灵活性。
 * 但是如果想要用另一个sql语句查询，就要写另一个rowMapper方法，就要修改抽象类，所以还是不够灵活.
 * 
 * 目前
 * 		sql使用过参数传入
 * 		rowMapper由子类实现
 * 
 * 从上面的问题可以看出，子类实现rowMapper还是不够灵活。
 * 
 * 那么能否使用一种方法来表达可变化性么？--都使用参数传入的方式，见UserDaoImplStrategy.java
 * 
 */

public class UserDaoImpl extends AbstractDao {

	public User findUser(String loginName, String password) {
		String sql = "select id, name, money, birthday  from user where name=?";
		Object[] args = new Object[] { loginName };
		Object user = super.find(sql, args);
		return (User) user;
	}

	public String findUserName(int id) {
		String sql = "select  name from user where id=?";
		Object[] args = new Object[] { id };
		Object user = super.find(sql, args);
		return ((User) user).getName();
	}

	protected Object rowMapper1(ResultSet rs) throws SQLException {
		return rs.getString("name");
	}

	protected Object rowMapper(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMoney(rs.getFloat("money"));
		user.setBirthday(rs.getDate("birthday"));
		return user;
	}

	public void delete(User user) {
		String sql = "delete from user where id=?";
		Object[] args = new Object[] { user.getId() };
		super.update(sql, args); // 通过参数传入
	}

	public void update(User user) {
		String sql = "update user set name=?, birthday=?, money=? where id=? ";
		Object[] args = new Object[] { user.getName(), user.getBirthday(),
				user.getMoney(), user.getId() };
		super.update(sql, args); // 通过参数传入
	}
}
