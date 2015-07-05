package com.zxyairings.codelib.jdbc.dao.refactor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zxyairings.codelib.jdbc.dao.DaoException;
import com.zxyairings.codelib.jdbc.util.JdbcUtils;

/*
 * 是针对UserDaoJdbcImpl.java的重构，这个类的问题是不通用，我们只能针对User这个对象和User这个表来增删改查。如果我们换个表和对象，那么就要新加一个十分类似的类。
 * 解决这个问题的入手点是：分离出，类中哪些是不变的(公共的，共用的)，哪些是变化的(特有的)。让后将不变的部分放入超类；处理变化的部分有两种方式：1. 变化的部分作为参数传入。2.变化的部分放在子类中做。
 * 如何分析？那就在原类上假设，如果要换一个对象，一个表，类中的方法中哪些部分变化了，哪些部分保持不变。
 * 		例如update方法：假设我们要更新的表Account，sql肯定是变化的，preparedStatement中所set的参数不一样。
 * 
 * 模板模式
 * 优化查询操作，区分开变化和不变的部分：
	sql和ResultSet的处理是变化部分，创建和释放资源部分是不变部分。
	提取超类，将不变部分放入超类，变化部分留给子类实现。
 * 
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
 * 那么能否使用一种方法来表达可变化性么？--都使用参数传入的方式，见MyDaoTemplate.java
 * 
 */
public abstract class AbstractDao {
	
	//模板方法模式
	//将不变的部分放入父类，变化的部分由子类实现
	public Object find(String sql, Object[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++)
				ps.setObject(i + 1, args[i]);
			rs = ps.executeQuery();
			Object obj = null;
			if (rs.next()) {
				obj = rowMapper(rs);
			}
			return obj;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
	//由子类实现变化的部分
	abstract protected Object rowMapper(ResultSet rs) throws SQLException;

	//将公共的部分放入父类，变化的部分在子类中由参数传入。
	public int update(String sql, Object[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++)
				ps.setObject(i + 1, args[i]);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
