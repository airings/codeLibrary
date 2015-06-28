package com.zxyairings.codelib.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.zxyairings.codelib.jdbc.util.JdbcUtils;

//CallableStatement 继承 PreparedStatement

/*
 * 以前的J2EE是两层架构(客户端+数据库)，没有业务逻辑层，那时存储过程充当了业务逻辑层和数据访问层。
 * 但是现在都是3层架构，我们将业务逻辑抽取出来用java来实现，而不再放在存储过程中，
 * 而且利用sql来实现业务逻辑也很复杂，所以现在使用存储过程越来越少了，
 * 除非我们想利用数据库中的某些特性，我们才将业务逻辑用存储过程实现。
 * 
 * 这里仅仅介绍如何在java中调用存储过程。存储过程的难点在如何写。
 */

public class StoredProcedureDemo {
	public static void main(String[] args) throws SQLException {
		ps();
	}

	static void ps() throws SQLException {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.创建语句

			String sql = "{ call addUser(?,?,?,?) } ";
			cs = conn.prepareCall(sql);
			cs.registerOutParameter(4, Types.INTEGER);//注册输入参数，sp执行完成后，就可以通过CallableStatement拿到返回结果
			cs.setString(1, "ps name");
			cs.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			cs.setFloat(3, 100f);

			cs.executeUpdate();

			int id = cs.getInt(4);

			System.out.println("id=" + id);
		} finally {
			JdbcUtils.free(rs, cs, conn);
		}
	}
}
