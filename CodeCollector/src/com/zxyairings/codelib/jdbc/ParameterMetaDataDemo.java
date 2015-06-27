package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//参数元数据，是针对PreparedStatement和CallableStatement的，因为他们有参数
public class ParameterMetaDataDemo {
	public static void main(String[] args) throws SQLException {
		Object[] params = new Object[] { "lisi", 100f };
		read("select * from user where name=? and  money > ?", params);
	}

	static void read(String sql, Object[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
//			ParameterMetaData pmd = ps.getParameterMetaData();
//			int count = pmd.getParameterCount();
			for (int i = 1; i <= params.length; i++) {
				// System.out.print(pmd.getParameterClassName(i) + "\t");
				// System.out.print(pmd.getParameterType(i) + "\t");
				// System.out.println(pmd.getParameterTypeName(i));
				ps.setObject(i, params[i - 1]);
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t"
						+ rs.getString("name") + "\t" + rs.getDate("birthday")
						+ "\t" + rs.getFloat("money"));
			}

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
