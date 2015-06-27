package com.zxyairings.codelib.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zxyairings.codelib.jdbc.JdbcUtils;
import com.zxyairings.codelib.jdbc.dao.DaoException;
import com.zxyairings.codelib.jdbc.dao.UserDao;
import com.zxyairings.codelib.jdbc.domain.User;

/*
 * 这里说明了SQLException这种异常该如何处理：
 * 1.一定不能在catch块中什么都不干，这种就相当于隐藏异常了
 * 2.如果仅仅是打印出异常的堆栈信息也是不够的，因为程序打印完异常后会继续往下走，而引起接下来的程序出现错误，这就相当于将错误转移到其他地方，最后使找最终原因时非常的困难。
 * 3.经过以上的因为那么就必须要让调用者知道被调用者出现了异常，即使业务逻辑层知道数据访问层出现了异常。所以被调用者如果出现了异常一般会抛出这个异常，使错误的原因暴露出来，那么可以简单的直接把扑捉到得异常抛出么？
 * 4.因为SQLException是checked异常，一旦抛出，那么调用者就必须catch，而且这还会引起被调用者的方法声明发生改变，即需要声明所要抛出的异常，从而引起数据访问层的接口发生变化。这也就破坏了三层架构之间的独立性，使得业务逻辑层通过这个异常依赖于数据访问层。
 * 5.综上所述，我们需要使用unchecked异常。如果业务逻辑层能够处理这个异常，那么他就可以catch并处理，如果处理不掉，也不用使用catch，那么就会继续向外抛；而且unchecked异常也不会改变接口。
 * 
 * 这里实现的代码能够基本上满足工作上的需要
 * 
 * JDBC使用模板示例，以后就按这个来写jdbc应用
 */

public class UserDaoJdbcImpl implements UserDao {

//	要插入新的用户，我们所需要的所有信息都在User这个对象中
	public void addUser(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into user(name,birthday, money) values (?,?,?) ";
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//这样写可以得到新插入数据行的主键
			ps.setString(1, user.getName());
			ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
			ps.setFloat(3, user.getMoney());
			ps.executeUpdate();
			
			rs=ps.getGeneratedKeys();
			if (rs.next()) {
				user.setId(rs.getInt(1));//将主键赋给user对象，这样数据库中的user跟java内存中的user对象对应了
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	public void delete(User user) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			String sql = "delete from user where id=" + user.getId();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, st, conn);
		}

	}

	public User findUser(String loginName, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select id, name, money, birthday  from user where name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginName);
			rs = ps.executeQuery();
			while (rs.next()) {
				user = mappingUser(rs);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return user;
	}

	public User getUser(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select id, name, money, birthday  from user where id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				user = mappingUser(rs);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return user;
	}

	private User mappingUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMoney(rs.getFloat("money"));
		user.setBirthday(rs.getDate("birthday"));
		return user;
	}

	public void update(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "update user set name=?, birthday=?, money=? where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
			ps.setFloat(3, user.getMoney());
			ps.setInt(4, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

}