package com.zxyairings.codelib.jdbc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//如何将大纯文本导入数据库

//java中的String没有大小限制。但是数据库中的varchar是有大小限制

public class ClobDemo {
	public static void main(String[] args) throws SQLException, IOException {
//		create();
		read();
	}

	static void read() throws SQLException, IOException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.创建语句
			st = conn.createStatement();

			// 4.执行语句
			rs = st.executeQuery("select big_text  from clob_test");

			// 5.处理结果
			while (rs.next()) {
				Clob clob = rs.getClob(1);//第一种方法
				Reader reader = clob.getCharacterStream();
				// reader = rs.getCharacterStream(1);//第二种方法，可以不getClob对象，直接get出Reader流来
				// String s = rs.getString(1); // 第三种方式，可以直接得到Clob的string，不容通过读取流来操作，只要内存可以装得下
				
				File file = new File("JdbUtils_bak.java");
				Writer writer = new BufferedWriter(new FileWriter(file));
				char[] buff = new char[1024];
				for (int i = 0; (i = reader.read(buff)) > 0;) {
					writer.write(buff, 0, i);
				}
				writer.close();
				reader.close();
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	//ctrl+1可以在文件中rename
	static void create() throws SQLException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			// 3.创建语句
			String sql = "insert into clob_test(big_text) values (?) ";
			ps = conn.prepareStatement(sql);
			File file = new File("src/com/zxyairings/codelib/jdbc/JdbcUtils.java");
			Reader reader = new BufferedReader(new FileReader(file));

			ps.setCharacterStream(1, reader, file.length());//如果包含中文字符或者Unicode字符，使用字符流
//			ps.setAsciiStream(1, x, length);//适合于文件中都是Asci码的字符
			// ps.setString(1, x_string); 还可以直接将流转换成string，直接赋值就可以了。
			// 4.执行语句
			int i = ps.executeUpdate();

			reader.close();

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
