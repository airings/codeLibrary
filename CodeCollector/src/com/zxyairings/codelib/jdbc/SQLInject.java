package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 在SQL中包含特殊字符或SQL的关键字(如：' or 1 or ')时Statement将出现不可预料的结果（出现异常或查询的结果不正确），可用PreparedStatement来解决。
 * 
 * 
 PreperedStatement（从Statement扩展而来）相对Statement的优点：
 1.没有SQL注入的问题。
 2.Statement会使数据库频繁编译SQL，可能造成数据库缓冲区溢出。
 3.数据库和驱动可以对PreperedStatement进行优化（只有在相关联的数据库连接没有关闭的情况下有效）。 

 使用PreperedStatement，会对sql这个字符串进行预处理，例如特殊字符的过滤，语句的优化等等。从而可以解决sql注入的问题。
 而且在多次运行同样的sql语句这样的情况下，PreperedStatement的效率比Statement要高，但是这也跟driver有关系。

 在使用jdbc操作数据库的步骤中，最花时间的是在第2步：建立连接 
 
 
 java.sql.Date 继承 java.util.Date
 java.sql.Date: 只有日期没有时间
 java.util.Date: 即有日期也有时间
 */

public class SQLInject {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		// readWithStatement("lisi");
		// readWithStatement("'or 1 or '");//如果传入这个，就会返回所有的结果，这就是sql注入

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++)
			readWithPreparedStatement("name1");
		long end = System.currentTimeMillis();
		System.out.println("readWithPreparedStatement:" + (end - start));

		start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++)
			readWithStatement("name1");
		end = System.currentTimeMillis();
		System.out.println("readWithStatement:" + (end - start));
	}

	// 使用PreparedStatement，PreparedStatement 继承 Statement
	static void readWithPreparedStatement(String name) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();

			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.创建语句
			String sql = "select id, name, money, birthday  from user where name=?";
			ps = conn.prepareStatement(sql); // 要在构造的时候就要传入sql
			ps.setString(1, name);
			// 4.执行语句
			rs = ps.executeQuery();// 这时不要再次传入sql了，注意：如果这里仍然传入sql，这时就会调用Statement的executeQuery，而不是PreparedStatement的。这时依然会报sql语句语法错误，因为mysql无法识别？

			// 5.处理结果
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t"
						+ rs.getString("name") + "\t" + rs.getDate("birthday")
						+ "\t" + rs.getFloat("money"));
			}

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	// 有问题的写法：sql注入
	static void readWithStatement(String name) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();

			// 3.创建语句
			String sql = "select id, name, money, birthday  from user where name='"
					+ name + "'";
			st = conn.createStatement();
			// 4.执行语句
			rs = st.executeQuery(sql);// 需要传入sql，这相当于直接在mysql中运行sql

			// 5.处理结果
			while (rs.next()) {
				System.out.println(rs.getObject("id") + "\t"
						+ rs.getObject("name") + "\t"
						+ rs.getObject("birthday") + "\t"
						+ rs.getObject("money"));
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

}