package com.zxyairings.codelib.jdbc.tx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zxyairings.codelib.jdbc.JdbcUtils;

/*
 * 所有事务都有的3个步骤
		connection.setAutoCommit(false);//打开事务。
		connection.commit();//提交事务。
		connection.rollback();//回滚事务。
		
这里的例子是在一个数据源上的事务，因为是Connection对象创建的事务，
如果我们想把跨越多个数据源的数据库操作放入到一个事务中，就要使用JTA容器实现事务。这种技术叫做分布式事务。

	JTA事务分成两阶段提交。
	javax.transaction.UserTransaction tx = (UserTransaction)ctx.lookup(“jndiName");
		tx.begin();
		//connection1 connection2 (可能来自不同的数据库)… 
	    tx.commit();//tx.rollback();
	
	JTA老师没有示例。
		
隔离级别：多线程并发读取数据时的正确性 

	脏读（dirty reads） 
	一个事务读取了另一个未提交的并行事务写的数据。 
	不可重复读（non-repeatable reads） 
	一个事务重新读取前面读取过的数据， 发现该数据已经被另一个已提交的事务修改过。 
	幻读（phantom read） 
	一个事务重新执行一个查询，返回一套符合查询条件的行， 发现这些行因为其他最近提交的事务而发生了改变。
	
	可串行化（Serializable ）是如何解决幻读？跟加锁很像，相当于给所读的表先加锁，别的事务无法更改table
	
	隔离级别越高，对性能的牺牲越大，是有代价的。

 */

public class TransactionDemo {
	public static void main(String[] args) throws SQLException {
		test();
	}

	static void test() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);//关闭自动提交，默认是打开的。
			//从这里开始
			//下面这一段就在一个事务中，满足事务的4个特性，要么全做，要么全不做
			
//					conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//设置该线程的隔离级别，只针对该线程来说，不会影响别人的隔离级别。线程结束，此设置就无效了。
					
					st = conn.createStatement();
					String sql = "update user set money=money-10 where id=1";
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
			//到这里结束
			conn.commit();//事务提交
		} catch (SQLException e) {
			if (conn != null)
				conn.rollback();//出现异常就可以回滚这段事务
			throw e;
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}
}
