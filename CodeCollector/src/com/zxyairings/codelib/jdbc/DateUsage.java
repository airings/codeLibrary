package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DateUsage {
	public static void main(String[] args) throws SQLException {
//		create("name2", new Date(), 500.0f);
		Date d = read(4);
		System.out.println(d);
	}

//	读取java.sql.Date数据赋给java.util.Date类型的变量，可以不用转化。注意输入格式的不同。
	static Date read(int id) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Date birthday = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.创建语句
			st = conn.createStatement();

			// 4.执行语句
			rs = st.executeQuery("select birthday  from user where id=" + id);

			// 5.处理结果
			while (rs.next()) {
				//birthday = new Date(rs.getDate("birthday").getTime());//输入格式保持java.util.Date的样子
				birthday = rs.getDate("birthday");//可以不用转化，因为是子类类型赋给父类类型的变量，做了一些格式转化的工作
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
		return birthday;
	}
	
	//将java.util.Date转换成java.sql.Date类型
	static void create(String name, Date birthday, float money)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.创建语句
			String sql = "insert into user(name,birthday, money) values (?, ?, ?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setDate(2, new java.sql.Date(birthday.getTime()));//将java.util.Date转换成java.sql.Date类型
			ps.setFloat(3, money);

			// 4.执行语句
			int i = ps.executeUpdate();
//			int i = ps.executeUpdate(sql);//这个会报sql语法错误的exception。原因是？

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
