package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
有两种方式实现工具类
1.使用静态方法：JdbcUtils
2.单例模式：JdbcUtilsSingleton
*/

public final class JdbcUtils {
	private static String url = "jdbc:mysql://localhost:3306/grampus";
	private static String user = "pfreader";
	private static String password = "mango1";

	private JdbcUtils() {}

	//注册驱动放入静态代码块，是为了保证只注册一次驱动
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
}
