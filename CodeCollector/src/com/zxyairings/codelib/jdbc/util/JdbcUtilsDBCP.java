package com.zxyairings.codelib.jdbc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

//使用第三方DBCP来创建数据源DataSource

/*
 * 
使用DBCP必须用的三个包：
	1.commons-dbcp-1.2.1.jar, commons-pool-1.2.jar, commons-collections-3.1.jar。
	2.配置参数。
	3.Java API: BasicDataSourceFactory.createDataSource(properties); 

 * 
 */

public class JdbcUtilsDBCP {
	private static String url = "jdbc:mysql://localhost:3306/grampus";
	private static String user = "pfreader";
	private static String password = "mango1";
	private static DataSource myDataSource = null;

	private JdbcUtilsDBCP() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// myDataSource = new MyDataSource2();
			Properties prop = new Properties();
			// prop.setProperty("driverClassName", "com.mysql.jdbc.Driver");
			// prop.setProperty("user", "user");

			InputStream is = JdbcUtils.class.getClassLoader()
					.getResourceAsStream("dbcpconfig.properties");
			prop.load(is);
			myDataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static DataSource getDataSource() {
		return myDataSource;
	}

	public static Connection getConnection() throws SQLException {
		// return DriverManager.getConnection(url, user, password);
		return myDataSource.getConnection();
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
						// myDataSource.free(conn);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
}
