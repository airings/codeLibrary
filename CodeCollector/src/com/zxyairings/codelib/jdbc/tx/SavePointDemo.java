package com.zxyairings.codelib.jdbc.tx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import com.zxyairings.codelib.jdbc.util.JdbcUtils;

/*
 * 当事务中出现问题，我们并不想全部回滚，因为有些步骤仍然是有意义的，我们想回滚到某个点。
 * 也就是，只想撤销事务中的部分操作时，这时就可使用SavePoint
 */

public class SavePointDemo {
	public static void main(String[] args) throws SQLException {
		test();
	}

	static void test() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Savepoint sp = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);			
			st = conn.createStatement();
			String sql = "update user set money=money-10 where id=1";//这个更新操作即使在此事务中，异常发生时也不会回滚
			st.executeUpdate(sql);
			
			sp = conn.setSavepoint();//例子的需求是如果出现问题，回滚到这个保存点
			sql = "update user set money=money-10 where id=3";
			st.executeUpdate(sql);

			sql = "select money from user where id=2";
			rs = st.executeQuery(sql);
			float money = 0.0f;
			if (rs.next()) {
				money = rs.getFloat("money");
			}
			if (money > 300)
				throw new RuntimeException("已经超过最大值！");

			sql = "update user set money=money+10 where id=2";
			st.executeUpdate(sql);

			conn.commit();
		} catch (RuntimeException e) {
			if (conn != null && sp != null) {
				conn.rollback(sp);//回滚到sp这个保存点
				conn.commit();
			}
			throw e;
		} catch (SQLException e) {
			if (conn != null)
				conn.rollback();//回滚全部事务
			throw e;
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}
}
