package com.zxyairings.codelib.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

//对框架特别有帮助，因为例如Hibernet可以通过这些database metadata，来做一些判断

public class DBMetaData {
	public static void main(String[] args) throws SQLException {
		java.sql.Connection conn = JdbcUtils.getConnection();
		DatabaseMetaData dbmd = conn.getMetaData();
		System.out.println("db name: " + dbmd.getDatabaseProductName());
		System.out.println("tx: " + dbmd.supportsTransactions());
		conn.close();
	}
}
