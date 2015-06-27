package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//结果集的元数据
//使用metadata方式使得sql执行非常的灵活

//现在是将结果集存入map，那么更进一步的就是存入user对象，这个就跟ORM很像了。参见ORMDemo.java
public class ResultSetMetaDataDemo {
	public static void main(String[] args) throws SQLException {
		List<Map<String, Object>> datas = read("select id, name as n from user where id < 5");
		System.out.println(datas);
	}

	//将结果集的放入map
	static List<Map<String, Object>> read(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			String[] colNames = new String[count];
			for (int i = 1; i <= count; i++) {
				// System.out.print(rsmd.getColumnClassName(i) + "\t");
				// System.out.print(rsmd.getColumnName(i) + "\t");
				// System.out.println(rsmd.getColumnLabel(i));//Label是列的别名
				colNames[i - 1] = rsmd.getColumnLabel(i);
			}
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

			while (rs.next()) {
				Map<String, Object> data = new HashMap<String, Object>();
				for (int i = 0; i < colNames.length; i++) {
					data.put(colNames[i], rs.getObject(colNames[i]));
				}
				datas.add(data);
			}
			return datas;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
