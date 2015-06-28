package com.zxyairings.codelib.jdbc.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

//模拟DataSource
//预先创建号数据库连接，放入集合中，如果需要用就直接从集合中取出，如果使用完毕就把它再加入集合中
//这种方式效率就很高，不会频繁的建立和销毁数据库连接。

// 以前是直接在工具类JdbcUtils中创建连接，现在将创建连接的工作放到MyDataSource中来。

//这个类的缺点就是关闭连接时，必须使用free方法，而不是以前的Connection.close方法，而这会改变用户的使用习惯。所以才会有MyDataSource2.java这个类

/*
 * MyDataSource与Connection之间的关系是：MyDataSource中有一个连接池，其中有很多的Connection对象。
 * 
DataSource用来取代DriverManager来获取Connection；
通过DataSource获得Connection速度很快；
通过DataSource获得的Connection都是已经被包裹过的（不是驱动原来的连接），他的close方法已经被修改。
一般DataSource内部会用一个连接池来缓存Connection，这样可以大幅度提高数据库的访问速度；
连接池可以理解成一个能够存放Connection的Collection；
我们的程序只和DataSource打交道，不会直接访问连接池；

 * 
 */

public class MyDataSource {
	private static String url = "jdbc:mysql://localhost:3306/grampus";
	private static String user = "pfreader";
	private static String password = "mango1";

	private static int initCount = 3;
	private static int maxCount = 10;
	private int currentCount = 0;

	LinkedList<Connection> connectionsPool = new LinkedList<Connection>();

	public MyDataSource() {
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
		return DriverManager.getConnection(url, user, password);
	}
}
