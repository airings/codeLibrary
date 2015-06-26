package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//工具类的单例实现示例
public final class JdbcUtilsSingleton {
	private static String url = "jdbc:mysql://localhost:3306/grampus";
	private static String user = "pfreader";
	private static String password = "mango1";

	// private static JdbcUtilsSing instance = new JdbcUtilsSing(); //饱汉式
	private static JdbcUtilsSingleton instance = null;

	private JdbcUtilsSingleton() {
	}

	public static JdbcUtilsSingleton getInstance() {
//		双重检查
		if (instance == null) {
			synchronized (JdbcUtilsSingleton.class) {
				if (instance == null) {
					instance = new JdbcUtilsSingleton(); //懒加载
				}
			}
		}
		return instance;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public void free(ResultSet rs, Statement st, Connection conn) {
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
