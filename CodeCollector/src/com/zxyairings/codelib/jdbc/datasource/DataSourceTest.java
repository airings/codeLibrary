package com.zxyairings.codelib.jdbc.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zxyairings.codelib.jdbc.util.JdbcUtils;
import com.zxyairings.codelib.jdbc.util.JdbcUtilsMyDataSource;

public class DataSourceTest {
	public static void main(String[] args) throws Exception {
//		testMyDataSource();//使用原生的Connection
//		testMyDataSource2();//使用自定义的MyConnection
		template();
		template();
		template();
		template();
	}

	private static void testMyDataSource() throws SQLException {
		for (int i = 0; i < 10; i++) {
			Connection conn = JdbcUtilsMyDataSource.getConnection();
//			System.out.println(conn.getClass().getName());
			System.out.println(conn);
			JdbcUtilsMyDataSource.free(null, null, conn);
		}
	}
	
	private static void testMyDataSource2() throws SQLException {
		for (int i = 0; i < 10; i++) {
			Connection conn = JdbcUtilsMyDataSource.getConnection2();
//			System.out.println(conn.getClass().getName());
			System.out.println(conn);
			JdbcUtilsMyDataSource.free(null, null, conn);
		}
	}
	
	static void template() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtilsMyDataSource.getConnection2();
			System.out.println(conn);
			// conn = JdbcUtilsSingleton.getInstance().getConnection();//单例工具类
			// 3.创建语句
			st = conn.createStatement();

			// 4.执行语句
			rs = st.executeQuery("select * from user");

			// 5.处理结果
			while (rs.next()) {
				// 参数中的1,2,3,4是指sql中的列索引
				System.out.println(rs.getObject(1) + "\t" + rs.getObject(2)
						+ "\t" + rs.getObject(3) + "\t" + rs.getObject(4));
			}
		} finally {
			JdbcUtilsMyDataSource.free(rs, st, conn);
		}

	}
	

}
