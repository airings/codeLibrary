package com.zxyairings.codelib.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import com.zxyairings.codelib.jdbc.util.JdbcUtils;

//对框架特别有帮助，因为例如Hibernet可以通过这些database metadata，来做一些判断
//通过DatabaseMetaData可以获得数据库相关的信息如：数据库版本、数据库名、数据库厂商信息、是否支持事务、是否支持某种事务隔离级别，是否支持滚动结果集等。


public class DBMetaData {
	public static void main(String[] args) throws SQLException {
		java.sql.Connection conn = JdbcUtils.getConnection();
		DatabaseMetaData dbmd = conn.getMetaData();
		System.out.println("db name: " + dbmd.getDatabaseProductName());
		System.out.println("tx: " + dbmd.supportsTransactions());
		conn.close();
	}
}
