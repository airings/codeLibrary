package com.zxyairings.codelib.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zxyairings.codelib.jdbc.domain.Bean;
import com.zxyairings.codelib.jdbc.domain.User;
import com.zxyairings.codelib.jdbc.util.JdbcUtils;

/*
 * improve ResultSetMetaDataDemo.java
 * 将数据库的返回结果放入到对象中去
 * 
 * 这个类的目的是了解ORM框架，例如 Hibernate，的实现原理，可能框架的实现更加复杂，但至少是类似的机制。
 * 
ResultSetMetaData meta = rs.getMetaData();
通过ResultSetMetaData可以获得结果有几列、各列名、各列别名、各列类型等。 
可以将ResultSet放入Map(key:列名 value:列值)。

用反射ResultSetMetaData将查询结果读入对象中（简单的O/RMapping） 
	1)让SQL语句中列别名和要读入的对象属性名一样；
	2)通过ResultSetMetaData获得结果列数和列别名；
	3)通过反射将对象的所有setXxx方法找到;
	4)将3）找到的方法setXxx和2）找到的列别名进行匹配（即方法中的xxx于列别名相等）；
	5)由上一步找到的方法和列别名对应关系进行赋值
	Method.invoke(obj, rs.getObject(columnAliasName));
 * 
 * 
 */



public class ORMDemo {

	public static void main(String[] args) throws SQLException,
			IllegalAccessException, InvocationTargetException, Exception {
//		User user = (User) getObject(
//				"select id as Id, name as Name, birthday as Birthday, money as Money  from user where id=1",
//				User.class);
//		System.out.println(user);

		Bean b = (Bean) getObject(
				"select id as Id, name as Name, birthday as Birthday, money as Money from user where id=1",
				Bean.class);
		System.out.println(b);
		
		
//		User user = getUser("select id as Id, name as Name, birthday as Birthday, money as Money  from user where id=1");
//		System.out.println(user);
	}
	
	//得到user对象，第一版
	public static User getUser(String sql) throws Exception {
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
				colNames[i - 1] = rsmd.getColumnLabel(i);
			}

			User user = null;
			Method[] ms = user.getClass().getMethods();

			if (rs.next()) {
				user = new User();
				for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					String methodName = "set" + colName; //这种方式确定的方法名肯定是不对的，因为不符合java bean的方法命名规则，应该通过内省的方式来做。这里李勇的解决方法是查询时给列名用as重命名
					System.out.println(methodName);
//					Method[] ms = user.getClass().getMethods();//放到循环外面
					for (Method method : ms) {
						if (methodName.equals(method.getName())) {
							method.invoke(user, rs.getObject(colName));
						}
					}
				}
			}
			return user;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
	
	//得到user对象，第二版: 解除了对User类的耦合，但是要求对象必须有无参构造函数
	static Object getObject(String sql, Class clazz) throws SQLException,
			Exception, IllegalAccessException, InvocationTargetException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			String[] colNames = getColNames(rs);

			Object object = null;
			Method[] ms = clazz.getMethods();
			if (rs.next()) {
				object = clazz.newInstance();//要求有无参构造函数
				for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					String methodName = "set" + colName;
					// Object value = rs.getObject(colName);
					// try {
					// Method m = clazz
					// .getMethod(methodName, value.getClass());
					// if (m != null)
					// m.invoke(object, value);
					// } catch (NoSuchMethodException e) {
					// e.printStackTrace();
					// //
					// }
					for (Method m : ms) {
						if (methodName.equals(m.getName())) {
							m.invoke(object, rs.getObject(colName));
							break;
						}
					}
				}
			}
			return object;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
	
	
	
	//得到user对象，第二版（另一种写法，泛型）: 解除了对User类的耦合，但是要求对象必须有无参构造函数
	public static <T> T getUser(String sql, Class<T> clazz) throws Exception {
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
				colNames[i - 1] = rsmd.getColumnLabel(i);
			}

			T obj = null;
			Method[] ms = clazz.getMethods();

			if (rs.next()) {
				obj = clazz.newInstance();
				for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					String methodName = "set" + colName; //这种方式确定的方法名肯定是不对的，因为不符合java bean的方法命名规则，应该通过内省的方式来做。这里李勇的解决方法是查询时给列名用as重命名
//					System.out.println(methodName);
//					Method[] ms = user.getClass().getMethods();//放到循环外面
					for (Method method : ms) {
						if (methodName.equals(method.getName())) {
							method.invoke(obj, rs.getObject(colName));
						}
					}
				}
			}
			return obj;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	static List<Object> getObjects(String sql, Class clazz)
			throws SQLException, Exception, IllegalAccessException,
			InvocationTargetException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			String[] colNames = getColNames(rs);

			List<Object> objects = new ArrayList<Object>();
			Method[] ms = clazz.getMethods();
			while (rs.next()) {
				Object object = clazz.newInstance();
				for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					String methodName = "set" + colName;
					// Object value = rs.getObject(colName);
					// try {
					// Method m = clazz
					// .getMethod(methodName, value.getClass());
					// if (m != null)
					// m.invoke(object, value);
					// } catch (NoSuchMethodException e) {
					// e.printStackTrace();
					// //
					// }
					for (Method m : ms) {
						if (methodName.equals(m.getName())) {
							m.invoke(object, rs.getObject(colName));
							break;
						}
					}
					objects.add(object);
				}
			}
			return objects;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	private static String[] getColNames(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		String[] colNames = new String[count];
		for (int i = 1; i <= count; i++) {
			colNames[i - 1] = rsmd.getColumnLabel(i);
		}
		return colNames;
	}
}
