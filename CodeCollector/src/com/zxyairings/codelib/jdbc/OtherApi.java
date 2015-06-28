package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zxyairings.codelib.jdbc.util.JdbcUtils;

public class OtherApi {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws SQLException,
			InterruptedException {
		int id = create();
		System.out.println("id:" + id);
		// read();
	}


	static void read() throws SQLException, InterruptedException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.创建语句
			st = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,//数据更新可感知
					ResultSet.CONCUR_UPDATABLE);//可更新结果集

			// 4.执行语句
			rs = st.executeQuery("select id, name, money, birthday  from user where id < 5");

			// 5.处理结果
			while (rs.next()) {
				int id = rs.getInt("id");
				System.out.println("show " + id + "...");
				Thread.sleep(10000);
				System.out.println(id + "\t" + rs.getObject("name") + "\t"
						+ rs.getObject("birthday") + "\t"
						+ rs.getObject("money"));
				
				//可更新的结果集: 在查询操作中做更新操作
//				String name = rs.getString("name");
//				if ("lisi".equals(name)) {
//					rs.updateFloat("money", 300f);
//					rs.updateRow();
//				}
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	// 返回插入数据行的主键
	static int create() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.创建语句
			String sql = "insert into user(name,birthday, money) values ('name2 gk', '1987-01-01', 400) ";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			int id = 0;
			if (rs.next())
				id = rs.getInt(1);
			return id;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
