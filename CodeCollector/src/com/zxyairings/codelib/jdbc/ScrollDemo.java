package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollDemo {
	public static void main(String[] args) throws SQLException {
		scroll();
	}

	static void scroll() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = st
					.executeQuery("select id, name, money, birthday  from user limit 150, 10");//mysql的分页语句
			while (rs.next()) {
				System.out.println(rs.getObject("id") + "\t"
						+ rs.getObject("name") + "\t"
						+ rs.getObject("birthday") + "\t"
						+ rs.getObject("money"));
			}

			System.out.println("------------");
			//从150行开始取10行
			//这就是数据分页，但是这种实现效率非常低，当数据库不支持分页时，可以利用可滚动的结果集实现分页的效果
			rs.absolute(150);//直接跳行，直接定位到150行
//			rs.afterLast();
//			rs.beforeFirst();
//			rs.last();
//			rs.first();
			
			int i = 0;
			while (rs.next() && i < 10) {
				i++;
				System.out.println(rs.getObject("id") + "\t"
						+ rs.getObject("name") + "\t"
						+ rs.getObject("birthday") + "\t"
						+ rs.getObject("money"));
			}

			// if (rs.previous())
			// System.out.println(rs.getObject("id") + "\t"
			// + rs.getObject("name") + "\t"
			// + rs.getObject("birthday") + "\t"
			// + rs.getObject("money"));

		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}
}
