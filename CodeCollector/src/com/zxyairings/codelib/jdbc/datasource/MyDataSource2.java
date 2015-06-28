package com.zxyairings.codelib.jdbc.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/*
 * 这个类的目的是为了不更改用户使用Connection对象的习惯：
 * 		用户关闭连接时，往往使用Connection.close方法，但是这个方法会真的关闭连接，而不是放入到连接池中。
 * 		所有就要重新复写close方法，这就是MyConnection类的目的
 */

public class MyDataSource2 {
	
	private static String url = "jdbc:mysql://localhost:3306/grampus";
	private static String user = "pfreader";
	private static String password = "mango1";

	private static int initCount = 1;
	private static int maxCount = 10;
	int currentCount = 0;

	LinkedList<Connection> connectionsPool = new LinkedList<Connection>();

	public MyDataSource2() {
		try {
			for (int i = 0; i < initCount; i++) {
				this.connectionsPool.addLast(this.createConnection());
				this.currentCount++;
			}
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public Connection getConnection() throws SQLException {
		synchronized (connectionsPool) {
			if (this.connectionsPool.size() > 0)
				return this.connectionsPool.removeFirst();

			if (this.currentCount < maxCount) {
				this.currentCount++;
				return this.createConnection();
			}

			throw new SQLException("已没有链接");
		}
	}

	public void free(Connection conn) {
		this.connectionsPool.addLast(conn);
	}

	private Connection createConnection() throws SQLException {
		Connection realConn = DriverManager.getConnection(url, user, password);
		// 静态代理:
//		MyConnection myConnection = new MyConnection(realConn, this);
//		return myConnection;
		// 动态代理:
		 MyConnectionHandler proxy = new MyConnectionHandler(this);
		 return proxy.bind(realConn);
	}
}
