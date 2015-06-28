package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import com.zxyairings.codelib.jdbc.util.JdbcUtils;

//batch

public class BatchDemo {
	public static void main(String[] args) throws SQLException {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++)
			create(i);//效率低
		long end = System.currentTimeMillis();
		System.out.println("create:" + (end - start));

		start = System.currentTimeMillis();
		createBatch();//高效：将循环放在内部
		end = System.currentTimeMillis();
		System.out.println("createBatch:" + (end - start));
	}

	static void create(int i) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into user(name,birthday, money) values (?, ?, ?) ";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, "no batch name" + i);
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.setFloat(3, 100f + i);

			ps.executeUpdate();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	//批处理
	static void createBatch() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into user(name,birthday, money) values (?, ?, ?) ";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < 100; i++) {
				ps.setString(1, "batch name" + i);
				ps.setDate(2, new Date(System.currentTimeMillis()));
				ps.setFloat(3, 100f + i);

				ps.addBatch();//batch也不是越大越好，如果一直加，有可能内存溢出
				//Statement也有addBatch方法，是将sql字符串传入
			}
			int[] is = ps.executeBatch();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
