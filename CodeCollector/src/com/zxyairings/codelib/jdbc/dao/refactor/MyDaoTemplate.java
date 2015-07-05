package com.zxyairings.codelib.jdbc.dao.refactor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zxyairings.codelib.jdbc.dao.DaoException;
import com.zxyairings.codelib.jdbc.util.JdbcUtils;

/*
 * 变化点：
 *  	sql使用过参数传入
 * 		rowMapper也由参数传入
 * 
 * rowMapper以前是抽象类中的一个抽象方法，现在是接口中的方法。
 * 
 * 策略模式：
 * 优化查询操作，区分开变化和不变的部分：
	sql和ResultSet的处理是变化部分，创建和释放资源部分是不变部分。
	提取接口封装变化部分。

 * 
 */

public class MyDaoTemplate {
	public Object find(String sql, Object[] args, RowMapper rowMapper) {
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
				obj = rowMapper.mapRow(rs);
			}
			return obj;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
