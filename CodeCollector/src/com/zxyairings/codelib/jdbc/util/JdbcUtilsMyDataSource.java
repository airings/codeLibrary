package com.zxyairings.codelib.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zxyairings.codelib.jdbc.datasource.MyDataSource;
import com.zxyairings.codelib.jdbc.datasource.MyDataSource2;

/*

使用 MyDataSource

之前是使用JdbcUtils来创建连接

现在是使用MyDataSource来创建连接池，而使用JdbcUtilsMyDataSource来作为统一的借口

MyDataSource是模拟DataSource

*/

public final class JdbcUtilsMyDataSource {
//	private static String url = "jdbc:mysql://localhost:3306/grampus";
//	private static String user = "pfreader";
//	private static String password = "mango1";
	
//	private static MyDataSource myDataSource = new MyDataSource();//这样写会报错，说找不到驱动，但是我们明明在下面加载了驱动，原因就是类的初始化顺序
	private static MyDataSource myDataSource = null;
	private static MyDataSource2 myDataSource2 = null;

	private JdbcUtilsMyDataSource() {}

	//注册驱动放入静态代码块，是为了保证只注册一次驱动
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			myDataSource = new MyDataSource();
			myDataSource2 = new MyDataSource2();
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		// return DriverManager.getConnection(url, user, password);
		return myDataSource.getConnection();
	}
	
	public static Connection getConnection2() throws SQLException {
		// return DriverManager.getConnection(url, user, password);
		return myDataSource2.getConnection();
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
						conn.close(); // 使用MyConnection后就不会改变用户习惯了，这里使用了代理模式，拦截close方法
//						myDataSource.free(conn); //这样就改变了用户的使用习惯
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
}
