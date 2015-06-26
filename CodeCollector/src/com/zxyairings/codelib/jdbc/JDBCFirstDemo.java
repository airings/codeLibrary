package com.zxyairings.codelib.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 连接数据的步骤
	1.注册驱动 (只做一次)
	2.建立连接(Connection) 
	3.创建执行SQL的语句(Statement)
	4.执行语句
	5.处理执行结果(ResultSet)
	6.释放资源

注册驱动
	Class.forName(“com.mysql.jdbc.Driver”);
		推荐这种方式，不会对具体的驱动类产生依赖。
	DriverManager.registerDriver(com.mysql.jdbc.Driver);
		会造成DriverManager中产生两个一样的驱动，并会对具体的驱动类产生依赖。
	System.setProperty(“jdbc.drivers”, “driver1:driver2”);
		虽然不会对具体的驱动类产生依赖；但注册不太方便，所以很少使用。 

建立连接
	Connection conn = DriverManager.getConnection(url, user, password);
	url格式：
		JDBC:子协议:子名称//主机名:端口/数据库名？属性名=属性值&…
	User,password可以用“属性名=属性值”方式告诉数据库；
	其他参数如：useUnicode=true&characterEncoding=GBK。

创建执行SQL的语句(Statement)
	Statement
		Statement st = conn.createStatement();
		st.executeQuery(sql);
	PreparedStatement
		String sql = “select * from table_name where col_name=?”;
		PreparedStatement ps = conn.preparedStatement(sql);
		ps.setString(1, “col_value”);
		ps.executeQuery();

处理执行结果(ResultSet)
	ResultSet rs = statement.executeQuery(sql);
	While(rs.next()){
		rs.getString(“col_name”);
		rs.getInt(“col_name”);
		//…
	}

释放资源
	释放ResultSet, Statement,Connection.
	数据库连接（Connection）是非常稀有的资源，用完后必须马上释放，如果Connection不能及时正确的关闭将导致系统宕机。Connection的使用原则是尽量晚创建，尽量早的释放。

 * */



public class JDBCFirstDemo {
	public static void main(String[] args) throws Exception {
//		test(); 
		template();

	}
	
	static void template() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 2.建立连接
			conn = JdbcUtils.getConnection();
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
			JdbcUtils.free(rs, st, conn);
		}

	}
	
	//JDBC使用模板示例，但是还有可以优化的地方，把一些共用的代码放入工具类JdbcUtils类中，优化后见template
	static void template_tmp() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String url = "jdbc:mysql://localhost:3306/grampus";
		String user = "pfreader";
		String password = "mango1";
		try {
			// 1.注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			// 2.建立连接
			conn = DriverManager.getConnection(url, user, password);
			
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
			try {
				if(rs!=null)
					rs.close();
			} finally {
				try {
					if(st != null)
						st.close();
				} finally {
					if(conn!=null)
						conn.close();
				}
			}
		}
	}

	//这个例子不是很规范，例如没有很好的处理异常
	static void test() throws SQLException, ClassNotFoundException {
		// 1.注册驱动，选择下面其一即可
		
		DriverManager.registerDriver(new com.mysql.jdbc.Driver()); //第一种方式。这样会引起类加载，所以他会使驱动加入驱动列表2次。而且，这样写法会对mysql的驱动存在依赖。
		System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");//第二种方式。DriverManager会读取jdbc.drivers这个系统属性。可以注册多个驱动，要用冒号分隔。
		Class.forName("com.mysql.jdbc.Driver");// 第三种方式。推荐方式。这种方式是如果加入驱动列表中呢？这时利用了在加载类之后会立刻执行类中的静态代码块，他就是在静态代码块中加入驱动列表的。

		// 2.建立连接
		String url = "jdbc:mysql://localhost:3306/grampus";
		String user = "pfreader";
		String password = "mango1";
		Connection conn = DriverManager.getConnection(url, user, password);

		// 3.创建语句
		Statement st = conn.createStatement();

		// 4.执行语句
		ResultSet rs = st.executeQuery("select * from user");

		// 5.处理结果
		while (rs.next()) {
			System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t"
					+ rs.getObject(3) + "\t" + rs.getObject(4));
		}

		// 6.释放资源
		rs.close();
		st.close();
		conn.close();
	}

}
